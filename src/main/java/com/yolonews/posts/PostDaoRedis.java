package com.yolonews.posts;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.yolonews.common.AbstractDaoRedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class PostDaoRedis extends AbstractDaoRedis<Post, Long> implements PostDao {
    @Inject
    public PostDaoRedis(JedisPool jedisPool) {
        super(jedisPool);
    }

    @Override
    protected Long handleSave(Jedis jedis, Post post) {
        long now = System.currentTimeMillis();
        if (post.getId() > 0) {
            // update
            post.setModifiedTime(now);
        } else {
            // create
            Long id = jedis.incr("posts.count");
            post.setId(id);
            post.setCreatedTime(now);
            post.setModifiedTime(now);
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
    protected Post fromMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Post post = new Post();
        post.setId(Long.valueOf(map.get("id")));
        post.setTitle(map.get("title"));
        post.setUrl(map.get("url"));
        post.setUserId(map.get("userId"));
        post.setScore(Long.valueOf(map.get("score")));
        post.setRank(Long.valueOf(map.get("rank")));
        post.setUpvotes(Long.valueOf(map.get("upvotes")));
        post.setDownvotes(Long.valueOf(map.get("downvotes")));
        post.setCreatedTime(Long.valueOf(map.get("createdTime")));
        post.setModifiedTime(Long.valueOf(map.get("modifiedTime")));
        return post;
    }

    @Override
    protected Map<String, String> toMap(Post post) {
        Preconditions.checkNotNull(post, "post is null");

        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(post.getId()));
        map.put("title", post.getTitle());
        map.put("url", post.getUrl());
        map.put("userId", post.getUserId());
        map.put("score", String.valueOf(post.getScore()));
        map.put("rank", String.valueOf(post.getRank()));
        map.put("upvotes", String.valueOf(post.getUpvotes()));
        map.put("downvotes", String.valueOf(post.getDownvotes()));
        map.put("createdTime", String.valueOf(post.getCreatedTime()));
        map.put("modifiedTime", String.valueOf(post.getModifiedTime()));
        return map;
    }
}
