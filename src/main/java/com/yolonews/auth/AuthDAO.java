package com.yolonews.auth;

import com.yolonews.common.Dao;

import java.util.OptionalLong;

/**
 * @author saket.mehta
 */
public interface AuthDAO extends Dao<String, String> {
    OptionalLong findUserByToken(String token);

    void insertToken(long userId, String token);

    void deleteToken(String token);
}
