package com.yolonews.comments;

import com.yolonews.common.BaseEntity;

/**
 * @author saket.mehta
 */
public class Comment extends BaseEntity {
    private String text;
    private Long userId;
    private Long parentId;
    private Long replyTo;
    private Long score;
    private Long upvotes;
    private Long downvotes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Long downvotes) {
        this.downvotes = downvotes;
    }
}
