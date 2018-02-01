package com.yolonews.auth;

import com.google.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

/**
 * @author saket.mehta
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthAPI {
    private final AuthService authService;

    @Inject
    public AuthAPI(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public Response login(Credentials credentials) {
        if (credentials.password != null && credentials.username != null) {
            Optional<String> token = authService.issueToken(credentials.username, credentials.password);
            if (token.isPresent()) {
                return Response.ok(token).build();
            }
        }
        return Response.status(FORBIDDEN).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@Context SecurityContext securityContext) {
        authService.revokeToken(securityContext.getUserPrincipal().getName());
        return Response.ok().build();
    }

    private static class Credentials {
        String username;
        String password;
    }
}
