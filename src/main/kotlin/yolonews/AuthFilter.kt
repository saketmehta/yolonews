package yolonews

import yolonews.api.Secured
import yolonews.services.AuthService
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

/**
 * @author saket.mehta
 */
@Secured
@Priority(Priorities.AUTHENTICATION)
class AuthFilter(private val tokenHeaderPrefix: String,
                 private val tokenQueryParamKey: String,
                 private val authService: AuthService
) : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext) {
        try {
            val token = extractToken(requestContext)
            authService.verifyToken(token)
        } catch (e: Exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        }
    }

    private fun extractToken(requestContext: ContainerRequestContext): String {
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader?.toLowerCase()?.startsWith(tokenHeaderPrefix.toLowerCase() + " ") ?: false) {
            return authorizationHeader.substring(tokenHeaderPrefix.length).trim()
        }
        return requestContext.uriInfo.queryParameters[tokenQueryParamKey]?.toString() ?: throw Exception()
    }
}