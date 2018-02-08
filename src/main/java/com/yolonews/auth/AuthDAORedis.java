package com.yolonews.auth;

import com.google.common.base.Preconditions;
import com.yolonews.common.AbstractDaoRedis;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class AuthDAORedis extends AbstractDaoRedis<String, String> implements AuthDAO {
    @Override
    public Optional<Long> findUserByToken(String token) {
        Preconditions.checkArgument(StringUtils.isEmpty(token), "token is empty");

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
        Preconditions.checkArgument(StringUtils.isEmpty(token), "token is empty");

        tryWithJedis(jedis -> jedis.set("auth:" + token, String.valueOf(userId)));
    }

    @Override
    public void deleteToken(String token) {
        Preconditions.checkArgument(StringUtils.isEmpty(token), "token is empty");

        tryWithJedis(jedis -> jedis.del("auth:" + token));
    }

    @Override
    protected String handleSave(Jedis jedis, String token) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Optional<String> handleFindById(Jedis jedis, String entityId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Void handleDelete(Jedis jedis, String entityId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected String fromMap(Map<String, String> map) {
        throw new UnsupportedOperationException("no need for this yet");
    }

    @Override
    protected Map<String, String> toMap(String s) {
        throw new UnsupportedOperationException("no need for this yet");
    }
}
