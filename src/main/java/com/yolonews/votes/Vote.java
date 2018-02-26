package com.yolonews.votes;

import com.yolonews.common.BaseEntity;

/**
 * @author saket.mehta
 */
public class Vote extends BaseEntity {
    private VoteType voteType;
    private long userId;
    private long postId;

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public enum VoteType {
        UP,
        DOWN
    }
}
