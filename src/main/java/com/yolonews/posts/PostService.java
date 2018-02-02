package com.yolonews.posts;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface PostService {
    Optional<Post> fetchPost(Long postId);

    Long createPost(Post post, Long userId);
}
