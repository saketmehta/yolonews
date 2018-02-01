package com.yolonews.comments;

/**
 * @author saket.mehta
 */
public interface CommentDAO {
    void insert();

    Comment findById();
}
