package com.yolonews.auth;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import redis.clients.jedis.Jedis;

import java.util.OptionalLong;
import java.util.function.Function;

/**
 * @author saket.mehta
 */
public class AuthDaoRedis implements AuthDAO {
    private static final String TOKEN = "auth:";

    private final Provider<Jedis> jedisProvider;

    @Inject
    AuthDaoRedis(Provider<Jedis> jedisProvider) {
        this.jedisProvider = jedisProvider;
    }

    @Override
    public OptionalLong findUserByToken(String token) {
        Preconditions.checkNotNull(token, "token is empty");

        return tryWithJedis(jedis -> {
            String userId = jedis.get(TOKEN + token);
            if (userId == null) {
                return OptionalLong.empty();
            }
            return OptionalLong.of(Long.parseLong(userId));
        });
    }

    @Override
    public void insertToken(long userId, String token) {
        Preconditions.checkArgument(userId > 0, "user id is not valid");
        Preconditions.checkNotNull(token, "token is empty");

        tryWithJedis(jedis -> jedis.set(TOKEN + token, String.valueOf(userId)));
    }

    @Override
    public void deleteToken(String token) {
        Preconditions.checkNotNull(token, "token is empty");

        tryWithJedis(jedis -> jedis.del(TOKEN + token));
    }

    private <E> E tryWithJedis(Function<Jedis, E> function) {
        try (Jedis jedis = jedisProvider.get()) {
            return function.apply(jedis);
        }
    }
}
