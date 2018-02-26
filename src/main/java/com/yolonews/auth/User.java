package com.yolonews.auth;

import com.yolonews.common.BaseEntity;

/**
 * @author saket.mehta
 */
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private long karma;

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

    public long getKarma() {
        return karma;
    }

    public void setKarma(long karma) {
        this.karma = karma;
    }
}
