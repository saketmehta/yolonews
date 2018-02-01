package com.yolonews.votes;

import com.google.inject.Inject;
import com.yolonews.common.BaseDAO;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
public class VoteDAORedis extends BaseDAO implements VoteDAO {

    @Inject
    public VoteDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    public void insert(Long postId, Long userId, VoteType voteType) {
        tryWithJedis(jedis -> {
            Long added = jedis.zadd("posts." + voteType.toString().toLowerCase() + ":" + postId,
                    System.currentTimeMillis(), String.valueOf(userId));
            if (added > 0) {
                String voteField = null;
                switch (voteType) {
                    case UP:
                        voteField = "upvotes";
                        break;
                    case DOWN:
                        voteField = "downvotes";
                        break;
                }
                jedis.hincrBy("posts:" + postId, voteField, 1);
            }
            return null;
        });
    }
}
