package com.yolonews.posts;

/**
 * @author saket.mehta
 */
public interface PostDAO {
    Long insert(String title, String url, String text, Long userId);

    Post findById(Long postId);
}
