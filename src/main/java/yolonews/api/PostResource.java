package yolonews.api;

import redis.clients.jedis.Jedis;
import yolonews.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author saket.mehta
 */
@Path("/api/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    private Jedis jedis = new Jedis("localhost");

    @GET
    @Path("/{id}")
    public Response fetchPost(@PathParam("id") Long id) {
        return null;
    }

    @POST
    public Response createPost(UserDTO userDTO) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public Response updatePost(@PathParam("id") Long id, UserDTO userDTO) {
        return null;
    }
}
