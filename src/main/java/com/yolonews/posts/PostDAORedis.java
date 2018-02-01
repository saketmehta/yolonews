package com.yolonews.posts;

import com.google.inject.Inject;
import com.yolonews.common.BaseDAO;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
public class PostDAORedis extends BaseDAO implements PostDAO {
    @Inject
    public PostDAORedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    public Long insert(String title, String url, String text, Long userId) {
        return tryWithJedis(jedis -> {
            Long id = jedis.incr("posts.count");
            Map<String, String> hash = new HashMap<>();
            hash.put("id", "id");
            hash.put("title", title);
            hash.put("url", url);
            hash.put("text", text);
            hash.put("userId", String.valueOf(userId));
            hash.put("createdTime", String.valueOf(System.currentTimeMillis()));
            hash.put("score", "0");
            hash.put("rank", "0");
            hash.put("upvotes", "0");
            hash.put("downvotes", "0");
            jedis.hmset("posts:" + id, hash);
            return id;
        });
    }

    @Override
    public Post findById(Long postId) {
        return tryWithJedis(jedis -> {
            Map<String, String> data = jedis.hgetAll("posts:" + postId);
            return Post.fromMap(data);
        });
    }
}
