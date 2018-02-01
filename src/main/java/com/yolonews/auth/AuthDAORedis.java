package com.yolonews.auth;

import com.google.inject.Inject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public class AuthDAORedis implements AuthDAO {
    private final JedisPool jedisPool;

    @Inject
    public AuthDAORedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Optional<Long> findUserByToken(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            String userId = jedis.get("auth:" + token);
            if (userId == null) {
                return Optional.empty();
            }
            return Optional.of(Long.valueOf(userId));
        }
    }

    @Override
    public void insertToken(Long userId, String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set("auth:" + token, String.valueOf(userId));
        }
    }

    @Override
    public void deleteToken(String token) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del("auth:" + token);
        }
    }
}
