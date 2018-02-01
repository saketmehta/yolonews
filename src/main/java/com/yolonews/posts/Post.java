package com.yolonews.posts;

import com.yolonews.common.BaseModel;

import java.util.Map;

/**
 * @author saket.mehta
 */
public class Post extends BaseModel {
    private Long id;
    private String title;
    private String url;
    private String text;
    private String userId;
    private Long score;
    private Long rank;
    private Long upvotes;
    private Long downvotes;

    public static Post fromMap(Map<String, String> data) {
        Post post = new Post();
        post.setId(Long.valueOf(data.get("id")));
        post.setTitle(data.get("title"));
        post.setUrl(data.get("url"));
        post.setText(data.get("text"));
        post.setUserId(data.get("userId"));
        post.setScore(Long.valueOf(data.get("score")));
        post.setRank(Long.valueOf(data.get("rank")));
        post.setUpvotes(Long.valueOf(data.get("upvotes")));
        post.setDownvotes(Long.valueOf(data.get("downvotes")));
        post.setCreatedTime(Long.valueOf(data.get("createdTime")));
        post.setModifiedTime(Long.valueOf(data.get("modifiedTime")));
        return post;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
