package com.yolonews.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
public enum JedisProvider {
    JEDIS_POOL;

    private JedisPool jedisPool;

    JedisProvider() {
        this.jedisPool = new JedisPool();
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }
}
