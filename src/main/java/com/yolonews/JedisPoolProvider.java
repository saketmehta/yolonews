package com.yolonews;

import com.google.inject.Provider;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
public class JedisPoolProvider implements Provider<JedisPool> {
    @Override
    public JedisPool get() {
        return new JedisPool();
    }
}