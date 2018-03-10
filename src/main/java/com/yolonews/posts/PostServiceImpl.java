package com.yolonews.posts;

import com.google.inject.Inject;
import com.yolonews.votes.Vote;
import com.yolonews.votes.VoteService;
import com.yolonews.votes.VoteType;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostServiceImpl implements PostService {
    private final PostDao postDAO;
    private final VoteService voteService;

    @Inject
    public PostServiceImpl(PostDao postDAO, VoteService voteService) {
        this.postDAO = postDAO;
        this.voteService = voteService;
    }

    @Override
    public Optional<Post> fetchPost(Long postId) {
        return postDAO.findById(postId);
    }

    @Override
    public Long createPost(Post post, Long userId) {
        Long postId = postDAO.save(post);
        Vote vote = new Vote(VoteType.UP.toString(), userId, postId);
        voteService.createVote(vote);
        return postId;
    }
}
