package com.yolonews.comments;

import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @author saket.mehta
 */
@Path("/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentAPI {
    private final CommentService commentService;

    @Inject
    public CommentAPI(CommentService commentService) {
        this.commentService = commentService;
    }

    @GET
    @Path("/{commentId}")
    public Response fetchComment(@PathParam("commentId") Long commentId) {
        Optional<Comment> comment = this.commentService.fetchComment(commentId);
        if (comment.isPresent()) {
            return Response.ok(comment.get()).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @POST
    @Path("/")
    public Response createComment(Comment comment) {
        Comment createdComment = this.commentService.createComment(comment);
        return Response.ok(createdComment).build();
    }
}
