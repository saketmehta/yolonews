package com.yolonews.posts;

import com.google.common.base.Enums;
import com.google.inject.Inject;
import com.yolonews.auth.Secured;
import com.yolonews.votes.Vote;
import com.yolonews.votes.VoteService;
import com.yolonews.votes.VoteType;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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
    private final VoteService voteService;

    @Inject
    public PostAPI(PostService postService, VoteService voteService) {
        this.postService = postService;
        this.voteService = voteService;
    }

    @GET
    @Path("/{postId}")
    public Response fetchPost(@PathParam("postId") Long postId) {
        Optional<Post> post = postService.fetchPost(postId);
        if (post.isPresent()) {
            return Response.ok(post).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @POST
    @Secured
    public Response createPost(PostDTO dto, @Context SecurityContext context) {
        String userId = context.getUserPrincipal().getName();
        Post post = dto.toPost();
        Long postId = postService.createPost(post, Long.valueOf(userId));
        return Response.ok(postId).build();
    }

    @POST
    @Path("/{postId}/votes")
    @Secured
    public Response voteOnPost(@PathParam("postId") Long postId, VoteDTO dto, @Context SecurityContext context) {
        String userId = context.getUserPrincipal().getName();
        Vote vote = new Vote();
        vote.setPostId(postId);
        vote.setUserId(Long.valueOf(userId));
        VoteType voteType = Enums.getIfPresent(VoteType.class, dto.voteType)
                .toJavaUtil()
                .orElseThrow(() -> new BadRequestException("invalid vote type"));
        vote.setVoteType(voteType);
        voteService.createVote(vote);
        return Response.ok().build();
    }

    private static class PostDTO {
        String title;
        String url;
        String text;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getText() {
            return text;
        }

        Post toPost() {
            Post post = new Post();
            post.setTitle(title);
            post.setUrl(url);
            if (url == null) {
                post.setUrl("text://" + text);
            }
            return post;
        }
    }

    private static class VoteDTO {
        String voteType;

        public String getVoteType() {
            return voteType;
        }
    }
}
