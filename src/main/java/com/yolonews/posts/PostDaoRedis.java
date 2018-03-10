package com.yolonews.posts;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.yolonews.common.AbstractDaoRedis;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostDaoRedis extends AbstractDaoRedis<Post, Long> implements PostDao {
    @Inject
    PostDaoRedis(Provider<Jedis> jedisProvider) {
        super(jedisProvider);
    }

    @Override
    protected Long handleSave(Jedis jedis, Post post) {
        if (post.getId() <= 0) {
            // create
            Long id = jedis.incr("posts.count");
            post.setId(id);
        }
        jedis.hmset("posts:" + post.getId(), toMap(post));
        return post.getId();
    }

    @Override
    protected Optional<Post> handleFindById(Jedis jedis, Long postId) {
        Map<String, String> data = jedis.hgetAll("posts:" + postId);
        Post post = fromMap(data);
        return Optional.ofNullable(post);
    }

    @Override
    protected Void handleDelete(Jedis jedis, Long postId) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected Class<Post> getEntityType() {
        return Post.class;
    }
}
