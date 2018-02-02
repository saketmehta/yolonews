package com.yolonews.posts;

import com.yolonews.common.BaseEntity;
import com.yolonews.common.Mappable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
public class Post extends BaseEntity implements Mappable {
    private Long id;
    private String title;
    private String url;
    private String userId;
    private Long score;
    private Long rank;
    private Long upvotes;
    private Long downvotes;

    @Override
    public void fromMap(Map<String, String> map) {
        this.setId(Long.valueOf(map.get("id")));
        this.setTitle(map.get("title"));
        this.setUrl(map.get("url"));
        this.setUserId(map.get("userId"));
        this.setScore(Long.valueOf(map.get("score")));
        this.setRank(Long.valueOf(map.get("rank")));
        this.setUpvotes(Long.valueOf(map.get("upvotes")));
        this.setDownvotes(Long.valueOf(map.get("downvotes")));
        this.setCreatedTime(Long.valueOf(map.get("createdTime")));
        this.setModifiedTime(Long.valueOf(map.get("modifiedTime")));
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(this.getId()));
        map.put("title", this.getTitle());
        map.put("url", this.getUrl());
        map.put("userId", this.getUserId());
        map.put("score", String.valueOf(this.getScore()));
        map.put("rank", String.valueOf(this.getRank()));
        map.put("upvotes", String.valueOf(this.getUpvotes()));
        map.put("downvotes", String.valueOf(this.getDownvotes()));
        map.put("createdTime", String.valueOf(this.getCreatedTime()));
        map.put("modifiedTime", String.valueOf(this.getModifiedTime()));
        return map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Long upvotes) {
        this.upvotes = upvotes;
    }

    public Long getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Long downvotes) {
        this.downvotes = downvotes;
    }
}
