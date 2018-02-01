package com.yolonews.posts;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface PostService {
    Optional<Post> fetchPost(Long postId);

    Post createPost(Post post);
}
