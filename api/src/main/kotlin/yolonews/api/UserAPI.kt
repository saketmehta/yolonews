package yolonews.api

import yolonews.models.User
import yolonews.services.UserService
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriInfo


/**
 * @author saket.mehta
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserAPI(private val userService: UserService) {
    @GET
    @Path("/{id}")
    fun fetchUser(@PathParam("id") id: Long): Response {
        val user = userService.fetchUser(id)
        return Response.ok(user).build()
    }

    @POST
    fun createUser(user: User, @Context uriInfo: UriInfo): Response {
        try {
            val createdUser = userService.createUser(user)
            val builder = uriInfo.absolutePathBuilder
            builder.path(createdUser.id.toString())
            return Response.created(builder.build()).entity(createdUser).build()
        } catch (e: Exception) {
            throw BadRequestException()
        }
    }

    @Secured
    @PUT
    @Path("/{id}")
    fun updateUser(user: User, @PathParam("id") id: Long): Response {
        userService.updateUser(user.copy(id = id))
        return Response.ok().build()
    }
}