package com.yolonews.votes;

import com.google.inject.Inject;

/**
 * @author saket.mehta
 */
public class VoteServiceImpl implements VoteService {
    private final VoteDao voteDAO;

    @Inject
    public VoteServiceImpl(VoteDao voteDAO) {
        this.voteDAO = voteDAO;
    }

    @Override
    public void createVote(Vote vote) {

    }
}
