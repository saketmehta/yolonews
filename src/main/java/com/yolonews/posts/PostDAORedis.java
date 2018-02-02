package com.yolonews.posts;

import com.google.inject.Inject;
import com.yolonews.common.AbstractDAORedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostDAORedis extends AbstractDAORedis<Post, Long> implements PostDAO {
    @Inject
    public PostDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    protected Long handleInsert(Jedis jedis, Post post) {
        Long id = jedis.incr("posts.count");
        post.setId(id);
        long now = System.currentTimeMillis();
        post.setCreatedTime(now);
        post.setModifiedTime(now);
        jedis.hmset("posts:" + id, post.toMap());
        return id;
    }

    @Override
    protected Optional<Post> handleFindById(Jedis jedis, Long postId) {
        Map<String, String> data = jedis.hgetAll("posts:" + postId);
        if (data == null || data.isEmpty()) {
            return Optional.empty();
        }
        Post post = new Post();
        post.fromMap(data);
        return Optional.of(post);
    }

    @Override
    protected Void handleUpdate(Jedis jedis, Post entity) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long entityId) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
