package com.yolonews;

import com.google.inject.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
public class YoloNewsTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Jedis.class).toProvider(JedisProviderTest.class);
    }

    @Provides
    JedisPool provideJedisPool() {
        return new JedisPool();
    }

    @Singleton
    public static class JedisProviderTest implements Provider<Jedis> {
        private final JedisPool jedisPool;

        @Inject
        public JedisProviderTest(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }

        @Override
        public Jedis get() {
            return this.jedisPool.getResource();
        }
    }
}
