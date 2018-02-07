package com.yolonews.auth;

import com.yolonews.common.Dao;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface AuthDAO extends Dao<String, String> {
    Optional<Long> findUserByToken(String token);

    void insertToken(Long userId, String token);

    void deleteToken(String token);
}
