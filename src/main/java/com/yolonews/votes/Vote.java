package com.yolonews.votes;

import com.yolonews.common.BaseEntity;
import com.yolonews.common.Mappable;

import java.util.Map;

/**
 * @author saket.mehta
 */
public class Vote extends BaseEntity implements Mappable {
    private Long id;
    private VoteType voteType;
    private Long userId;
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public void fromMap(Map<String, String> map) {
        throw new UnsupportedOperationException("no need for this yet");
    }

    @Override
    public Map<String, String> toMap() {
        throw new UnsupportedOperationException("no need for this yet");
    }

    public enum VoteType {
        UP,
        DOWN
    }
}
