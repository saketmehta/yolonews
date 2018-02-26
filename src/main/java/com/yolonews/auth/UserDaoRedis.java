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
public class UserDaoRedis extends AbstractDaoRedis<User, Long> implements UserDao {
    @Override
    public Optional<User> findByUsername(String username) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(username), "username is empty");
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
        if (user.getId() > 0) {
            // update
            jedis.hmset("user:" + user.getId(), toMap(user));
            jedis.set("username.to.id:" + user.getUsername(), String.valueOf(user.getId()));
        } else {
            // create
            Long id = jedis.incr("users.count");
            user.setId(id);
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
    protected Class<User> getEntityType() {
        return User.class;
    }
}
