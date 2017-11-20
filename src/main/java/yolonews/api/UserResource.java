package yolonews.api;

import redis.clients.jedis.Jedis;
import yolonews.User;
import yolonews.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private Jedis jedis = new Jedis("localhost");

    @GET
    @Path("/{id}")
    public User fetchUser(@PathParam("id") Long id) {
        Map<String, String> data = jedis.hgetAll("user:" + id);
        return toUser(data);
    }

    @POST
    public void createUser(UserDTO userDTO) {
        Boolean exists = jedis.exists("username.to.id:" + userDTO.getUsername().toLowerCase());
        if (exists) {
            return;
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
    }

    @PUT
    @Path("/{id}")
    public void updateUser(UserDTO userDTO) {
        jedis.hset("user:" + userDTO.getId(), "email", userDTO.getEmail());
        if (userDTO.getPassword() != null || userDTO.getPassword().length() > 0) {
            jedis.hset("user:" + userDTO.getId(), "password", userDTO.getPassword());
        }
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
