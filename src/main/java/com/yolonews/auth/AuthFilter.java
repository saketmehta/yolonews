package com.yolonews.auth;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.security.Principal;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author saket.mehta
 */
@Secured
public class AuthFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        Principal principal = requestContext.getSecurityContext().getUserPrincipal();
        if (principal == null) {
            requestContext.abortWith(Response.status(UNAUTHORIZED).build());
        }
    }
}
