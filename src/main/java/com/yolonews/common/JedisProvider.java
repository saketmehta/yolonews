package com.yolonews.common;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
@Singleton
public class JedisProvider implements Provider<Jedis> {
    private final JedisPool jedisPool;

    @Inject
    public JedisProvider(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public Jedis get() {
        return this.jedisPool.getResource();
    }
}
