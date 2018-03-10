package com.yolonews.auth;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.yolonews.common.AbstractDaoRedis;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class UserDaoRedis extends AbstractDaoRedis<User, Long> implements UserDao {
    private static final String USERNAME_TO_ID_KEY = "username.to.id:";
    private static final String USER_KEY = "user:";
    private static final String USER_COUNT_KEY = "users.count";

    @Inject
    UserDaoRedis(Provider<Jedis> jedisProvider) {
        super(jedisProvider);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Preconditions.checkNotNull(username, "username is empty");
        return tryWithJedis(jedis -> {
            String userId = jedis.get(USERNAME_TO_ID_KEY + username);
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
            Optional<User> exists = findById(user.getId());
            if (exists.isPresent()) {
                jedis.hmset(USER_KEY + user.getId(), toMap(user));
                jedis.set(USERNAME_TO_ID_KEY + user.getUsername(), String.valueOf(user.getId()));
            } else {
                throw new IllegalArgumentException("the mentioned user id does not exist");
            }
        } else {
            // create
            Long id = jedis.incr(USER_COUNT_KEY);
            user.setId(id);
            jedis.hmset(USER_KEY + id, toMap(user));
            jedis.set(USERNAME_TO_ID_KEY + user.getUsername(), String.valueOf(id));
        }
        return user.getId();
    }

    @Override
    protected Optional<User> handleFindById(Jedis jedis, Long userId) {
        Map<String, String> data = jedis.hgetAll(USER_KEY + userId);
        if (data.isEmpty()) {
            return Optional.empty();
        }
        User user = fromMap(data);
        return Optional.ofNullable(user);
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long userId) {
        String username = jedis.hget(USER_KEY + userId, "username");
        jedis.hdel(USER_KEY + userId, "username", "email", "password");
        jedis.del(USERNAME_TO_ID_KEY + username);
        return null;
    }

    @Override
    protected Class<User> getEntityType() {
        return User.class;
    }
}
