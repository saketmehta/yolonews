package com.yolonews.votes;

import com.google.inject.Inject;
import com.yolonews.common.AbstractDAORedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class VoteDAORedis extends AbstractDAORedis<Vote, Long> implements VoteDAO {
    @Inject
    public VoteDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    protected Long handleSave(Jedis jedis, Vote vote) {
        throw new UnsupportedOperationException("not yet implemented");
//        Long added = jedis.zadd("posts." + vote.getVoteType().toString().toLowerCase() + ":" + vote.getPostId(),
//                vote.getCreatedTime(), String.valueOf(vote.getUserId()));
//        if (added > 0) {
//            String voteField = null;
//            switch (vote.getVoteType()) {
//                case UP:
//                    voteField = "upvotes";
//                    break;
//                case DOWN:
//                    voteField = "downvotes";
//                    break;
//            }
//            jedis.hincrBy("posts:" + vote.getPostId(), voteField, 1);
//        }
//        return null;
    }

    @Override
    protected Optional<Vote> handleFindById(Jedis jedis, Long voteId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long voteId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Vote fromMap(Map<String, String> map) {
        throw new UnsupportedOperationException("no need for this yet");
    }

    @Override
    protected Map<String, String> toMap(Vote vote) {
        throw new UnsupportedOperationException("no need for this yet");
    }
}
