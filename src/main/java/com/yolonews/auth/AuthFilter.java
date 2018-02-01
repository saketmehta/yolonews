package com.yolonews.auth;

import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author saket.mehta
 */
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    private final AuthService authService;

    @Inject
    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        extractToken(requestContext).ifPresentOrElse(token -> {
            Optional<User> authenticatedUser = authService.verifyToken(token);
            authenticatedUser.ifPresentOrElse(user -> {
                SecurityContext currentSecurityContext = requestContext.getSecurityContext();
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> String.valueOf(user.getId());
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return currentSecurityContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return AUTHENTICATION_SCHEME;
                    }
                });
            }, () -> requestContext.abortWith(Response.status(UNAUTHORIZED).build()));
        }, () -> requestContext.abortWith(Response.status(UNAUTHORIZED).build()));
    }

    private Optional<String> extractToken(ContainerRequestContext requestContext) {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            return Optional.empty();
        } else {
            authorizationHeader = authorizationHeader.toLowerCase();
            if (authorizationHeader.startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ")) {
                return Optional.of(authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim());
            } else {
                return Optional.empty();
            }
        }
    }
}
