package com.yolonews.auth;

import com.yolonews.common.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
public class User extends BaseModel {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long karma;

    public static User fromMap(Map<String, String> map) {
        if (map == null) {
            throw new IllegalArgumentException("map is null");
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

    public static Map<String, String> toMap(User user) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getKarma() {
        return karma;
    }

    public void setKarma(Long karma) {
        this.karma = karma;
    }
}
