package com.yolonews.posts;

import com.google.inject.Inject;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostServiceImpl implements PostService {
    private final PostDao postDAO;

    @Inject
    public PostServiceImpl(PostDao postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public Optional<Post> fetchPost(Long postId) {
        return postDAO.findById(postId);
    }

    @Override
    public Long createPost(Post post, Long userId) {
        return postDAO.save(post);
    }
}
