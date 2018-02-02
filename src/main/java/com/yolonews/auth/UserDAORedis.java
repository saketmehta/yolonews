package com.yolonews.auth;

import com.google.inject.Inject;
import com.yolonews.common.AbstractDAORedis;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class UserDAORedis extends AbstractDAORedis<User, Long> implements UserDAO {
    @Inject
    public UserDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    protected Long handleInsert(Jedis jedis, User user) {
        if (jedis.exists("username.to.id:" + user.getUsername().toLowerCase())) {
            throw new IllegalArgumentException("username taken");
        }
        Long id = jedis.incr("users.count");
        user.setId(id);
        long now = System.currentTimeMillis();
        user.setCreatedTime(now);
        user.setModifiedTime(now);
        jedis.hmset("user:" + id, user.toMap());
        jedis.set("username.to.id:" + user.getUsername(), String.valueOf(id));
        return id;
    }

    @Override
    protected Optional<User> handleFindById(Jedis jedis, Long userId) {
        Map<String, String> data = jedis.hgetAll("user:" + userId);
        if (data != null && !data.isEmpty()) {
            User user = new User();
            user.fromMap(data);
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected Void handleUpdate(Jedis jedis, User user) {
        if (jedis.exists("user:" + user.getId())) {
            findById(user.getId()).map(u -> {
                u.setEmail(user.getEmail());
                u.setUsername(user.getUsername());
                u.setModifiedTime(System.currentTimeMillis());
                return u;
            }).ifPresent(u -> jedis.hmset("user:" + user.getId(), user.toMap()));
        }
        return null;
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long entityId) {
        throw new UnsupportedOperationException("not yet implemented");
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
}
