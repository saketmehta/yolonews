package yolonews.api

import yolonews.models.Post
import yolonews.services.PostService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author saket.mehta
 */
@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PostAPI(private val postService: PostService) {
    @GET
    @Path("/{id}")
    fun fetchPost(@PathParam("id") id: Long): Response {
        if (id < 1) {
            throw BadRequestException()
        }
        val post = postService.fetchPost(id)
        return Response.ok().build()
    }

    @POST
    fun createPost(post: Post): Response {
        val id = postService.createPost(post)
        return Response.ok(id).build()
    }
}