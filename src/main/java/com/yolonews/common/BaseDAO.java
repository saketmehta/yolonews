package com.yolonews.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.function.Function;

/**
 * @author saket.mehta
 */
public abstract class BaseDAO {
    private final JedisPool jedisPool;

    protected BaseDAO(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    protected <E> E tryWithJedis(Function<Jedis, E> function) {
        try (Jedis jedis = jedisPool.getResource()) {
            return function.apply(jedis);
        }
    }
}
