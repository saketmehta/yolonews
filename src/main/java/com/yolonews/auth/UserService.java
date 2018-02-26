package com.yolonews.auth;

import java.util.Optional;
import java.util.OptionalLong;

/**
 * @author saket.mehta
 */
public interface UserService {
    OptionalLong createUser(User user);

    Optional<User> fetchUser(Long userId);

    void updateUser(Long userId, User user);
}
