package com.yolonews.auth;

import com.google.common.base.Preconditions;
import com.yolonews.common.AbstractDaoRedis;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class UserDaoRedis extends AbstractDaoRedis<User, Long> implements UserDao {
    @Override
    public Optional<User> findByUsername(String username) {
        Preconditions.checkArgument(StringUtils.isEmpty(username), "username is empty");

        return tryWithJedis(jedis -> {
            String userId = jedis.get("username.to.id:" + username);
            if (StringUtils.isEmpty(userId)) {
                return Optional.empty();
            }
            return findById(Long.valueOf(userId));
        });
    }

    @Override
    protected Long handleSave(Jedis jedis, User user) {
        long now = System.currentTimeMillis();
        if (user.getId() > 0) {
            // update
            user.setModifiedTime(now);
            jedis.hmset("user:" + user.getId(), toMap(user));
            jedis.set("username.to.id:" + user.getUsername(), String.valueOf(user.getId()));
        } else {
            // create
            Long id = jedis.incr("users.count");
            user.setId(id);
            user.setCreatedTime(now);
            user.setModifiedTime(now);
            jedis.hmset("user:" + id, toMap(user));
            jedis.set("username.to.id:" + user.getUsername(), String.valueOf(id));
        }
        return user.getId();
    }

    @Override
    protected Optional<User> handleFindById(Jedis jedis, Long userId) {
        Map<String, String> data = jedis.hgetAll("user:" + userId);
        User user = fromMap(data);
        return Optional.ofNullable(user);
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long userId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected User fromMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setId(Long.valueOf(map.get("id")));
        user.setEmail(map.get("email"));
        user.setUsername(map.get("username"));
        user.setPassword(map.get("password"));
        user.setKarma(Long.valueOf(map.get("karma")));
        user.setCreatedTime(Long.valueOf(map.get("createdTime")));
        user.setModifiedTime(Long.valueOf(map.get("modifiedTime")));
        return user;
    }

    @Override
    protected Map<String, String> toMap(User user) {
        Preconditions.checkNotNull(user, "user is null");

        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(user.getId()));
        result.put("email", user.getEmail());
        result.put("username", user.getUsername());
        result.put("password", user.getPassword());
        result.put("karma", String.valueOf(user.getKarma()));
        result.put("createdTime", String.valueOf(user.getCreatedTime()));
        result.put("modifiedTime", String.valueOf(user.getModifiedTime()));
        return result;
    }
}
