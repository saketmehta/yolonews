package com.yolonews.auth;

import com.yolonews.common.BaseEntity;
import com.yolonews.common.Mappable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saket.mehta
 */
public class User extends BaseEntity implements Mappable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long karma;

    @Override
    public void fromMap(Map<String, String> map) {
        if (map == null) {
            throw new IllegalArgumentException("map is null");
        }
        this.setId(Long.valueOf(map.get("id")));
        this.setEmail(map.get("email"));
        this.setUsername(map.get("username"));
        this.setPassword(map.get("password"));
        this.setKarma(Long.valueOf(map.get("karma")));
        this.setCreatedTime(Long.valueOf(map.get("createdTime")));
        this.setModifiedTime(Long.valueOf(map.get("modifiedTime")));
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();
        result.put("id", String.valueOf(this.getId()));
        result.put("email", this.getEmail());
        result.put("username", this.getUsername());
        result.put("password", this.getPassword());
        result.put("karma", String.valueOf(this.getKarma()));
        result.put("createdTime", String.valueOf(this.getCreatedTime()));
        result.put("modifiedTime", String.valueOf(this.getModifiedTime()));
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
