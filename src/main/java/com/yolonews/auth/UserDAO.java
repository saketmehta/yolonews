package com.yolonews.auth;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface UserDAO {
    Optional<User> findById(long userId);

    Optional<User> findByUsername(String username);

    Long insert(String username, String password, String email);
}
