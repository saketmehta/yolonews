package com.yolonews.comments;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface CommentService {
    Optional<Comment> fetchComment(Long commentId);

    Comment createComment(Comment comment);
}
