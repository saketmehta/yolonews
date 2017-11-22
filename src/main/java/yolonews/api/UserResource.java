package yolonews.api;

import redis.clients.jedis.Jedis;
import yolonews.dto.UserDTO;
import yolonews.models.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
@Path("/api/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private Jedis jedis = new Jedis("localhost");

    @GET
    @Path("/{id}")
    public Response fetchUser(@PathParam("id") Long id) {
        Map<String, String> data = jedis.hgetAll("user:" + id);
        if (data.size() == 0) {
            throw new NotFoundException();
        }
        User user = toUser(data);
        return Response.ok(user).build();
    }

    @POST
    public Response createUser(UserDTO userDTO) {
        Boolean exists = jedis.exists("username.to.id:" + userDTO.getUsername().toLowerCase());
        if (exists) {
            throw new BadRequestException();
        }
        Long id = jedis.incr("users.count");
        Map<String, String> data = new HashMap<>();
        data.put("id", id.toString());
        data.put("username", userDTO.getUsername());
        data.put("password", userDTO.getPassword());
        data.put("karma", "0");
        data.put("email", "");
        data.put("createdTime", String.valueOf(System.currentTimeMillis()));
        jedis.hmset("user:" + id, data);
        jedis.set("username.to.id:" + userDTO.getUsername(), id.toString());
        return Response.ok(data).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(UserDTO userDTO, @PathParam("id") Long id) {
        jedis.hset("user:" + id, "email", userDTO.getEmail());
        if (userDTO.getPassword() != null && userDTO.getPassword().length() > 0) {
            jedis.hset("user:" + id, "password", userDTO.getPassword());
        }
        return Response.ok().build();
    }

    private User toUser(Map<String, String> data) {
        return new User(Integer.parseInt(data.get("id")),
                data.get("username"),
                data.get("password"),
                Integer.parseInt(data.get("karma")),
                data.get("email"),
                Long.parseLong(data.get("createdTime")));
    }
}
