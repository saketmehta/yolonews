package com.yolonews.common;

import com.google.inject.Provider;
import redis.clients.jedis.Jedis;

/**
 * @author saket.mehta
 */
public class JedisProviderTest implements Provider<Jedis> {
    @Override
    public Jedis get() {
        return new Jedis();
    }
}
