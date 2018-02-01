package com.yolonews.posts;

import com.google.inject.Inject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * @author saket.mehta
 */
@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostAPI {
    private final PostService postService;

    @Inject
    public PostAPI(PostService postService) {
        this.postService = postService;
    }

    @GET
    @Path("/{postId}")
    public Response fetchPost(@PathParam("postId") Long postId) {
        Optional<Post> post = this.postService.fetchPost(postId);
        if (post.isPresent()) {
            return Response.ok(post).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @POST
    @Path("/")
    public Response createPost(PostDTO dto) {
        Post post = dto.toPost();
        post.setScore(1L);
        post.setUpvotes(1L);
        post.setDownvotes(0L);
        Post createdPost = postService.createPost(post);
        return Response.ok(createdPost).build();
    }

    private static class PostDTO {
        String title;
        String url;
        String text;

        Post toPost() {
            Post post = new Post();
            post.setTitle(title);
            post.setUrl(url);
            post.setText(text);
            return post;
        }
    }
}
