package com.yolonews.votes;

import com.yolonews.common.BaseEntity;

/**
 * @author saket.mehta
 */
public class Vote extends BaseEntity {
    private String voteType;
    private long userId;
    private long postId;

    public Vote(String voteType, long userId, long postId) {
        this.voteType = voteType;
        this.userId = userId;
        this.postId = postId;
    }

    public String getVoteType() {
        return voteType;
    }

    public long getUserId() {
        return userId;
    }

    public long getPostId() {
        return postId;
    }
}
