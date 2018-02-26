package com.yolonews.auth;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.function.Function;

import static com.yolonews.common.JedisProvider.JEDIS_POOL;

/**
 * @author saket.mehta
 */
public class AuthDAORedis implements AuthDAO {
    @Override
    public Optional<Long> findUserByToken(String token) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(token), "token is empty");

        return tryWithJedis(jedis -> {
            String userId = jedis.get("auth:" + token);
            if (userId == null) {
                return Optional.empty();
            }
            return Optional.of(Long.valueOf(userId));
        });
    }

    @Override
    public void insertToken(Long userId, String token) {
        Preconditions.checkNotNull(userId, "userId is null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(token), "token is empty");

        tryWithJedis(jedis -> jedis.set("auth:" + token, String.valueOf(userId)));
    }

    @Override
    public void deleteToken(String token) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(token), "token is empty");

        tryWithJedis(jedis -> jedis.del("auth:" + token));
    }

    private <E> E tryWithJedis(Function<Jedis, E> function) {
        try (Jedis jedis = JEDIS_POOL.getResource()) {
            return function.apply(jedis);
        }
    }
}
