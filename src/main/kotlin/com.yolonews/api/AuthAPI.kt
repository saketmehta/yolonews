package com.yolonews.api

import com.yolonews.services.AuthService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author saket.mehta
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class AuthAPI(private val authService: AuthService) {
    @POST
    @Path("/login")
    fun login(credentials: Credentials): Response {
        try {
            val token = authService.issueToken(credentials.username, credentials.password)
            return Response.ok(token).build()
        } catch (ex: Exception) {
            throw ForbiddenException()
        }
    }

    @POST
    @Path("/logout")
    fun logout(@QueryParam("key") apiKey: String): Response {
        authService.revokeToken(apiKey)
        return Response.ok().build()
    }

    data class Credentials(val username: String, val password: String)
}
