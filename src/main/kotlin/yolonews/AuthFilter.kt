package yolonews

import yolonews.services.AuthService
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response

/**
 * @author saket.mehta
 */
class AuthFilter(private val tokenHeaderPrefix: String,
                 private val tokenQueryParamKey: String,
                 private val authService: AuthService
) : ContainerRequestFilter {
    @Context
    private val request: HttpServletRequest? = null

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
        return request?.getParameter(tokenQueryParamKey) ?: throw Exception()
    }
}