package com.yolonews.votes;

/**
 * @author saket.mehta
 */
public interface VoteDAO {
    void insert(Long postId, Long userId, VoteType voteType);
}
