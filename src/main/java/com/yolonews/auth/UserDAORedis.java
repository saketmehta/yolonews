package com.yolonews.auth;

import com.google.inject.Inject;
import com.yolonews.common.BaseDAO;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class UserDAORedis extends BaseDAO implements UserDAO {
    @Inject
    public UserDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    public Optional<User> findById(long userId) {
        return tryWithJedis(jedis -> {
            Map<String, String> data = jedis.hgetAll("user:" + userId);
            if (data != null) {
                User user = User.fromMap(data);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("username is null");
        }
        return tryWithJedis(jedis -> {
            String userId = jedis.get("username.to.id:" + username);
            if (userId == null) {
                return Optional.empty();
            }
            return findById(Long.valueOf(userId));
        });
    }

    @Override
    public Long insert(String username, String password, String email) {
        return tryWithJedis(jedis -> {
            if (jedis.exists("username.to.id:" + username.toLowerCase())) {
                throw new IllegalArgumentException("username taken");
            }
            Long id = jedis.incr("users.count");
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(id));
            data.put("username", username);
            data.put("password", password);
            data.put("email", email);
            data.put("karma", String.valueOf(1));
            jedis.hmset("user:" + id, data);
            jedis.set("username.to.id:" + username, String.valueOf(id));
            return id;
        });
    }
}
