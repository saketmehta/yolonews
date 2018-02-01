package com.yolonews.auth;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface AuthDAO {
    Optional<Long> findUserByToken(String token);

    void insertToken(Long userId, String token);

    void deleteToken(String token);
}
