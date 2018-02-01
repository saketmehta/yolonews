package com.yolonews.auth;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface UserService {
    Long createUser(User user);

    Optional<User> fetchUser(Long userId);

    User updateUser(Long userId, String newUsername, String newEmail);
}
