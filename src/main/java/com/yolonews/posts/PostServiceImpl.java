package com.yolonews.posts;

import com.google.inject.Inject;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;

    @Inject
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public Optional<Post> fetchPost(Long postId) {
        return Optional.empty();
    }

    @Override
    public Post createPost(Post post) {
        return null;
    }
}
