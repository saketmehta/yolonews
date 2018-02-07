package com.yolonews.auth;

import com.google.inject.Inject;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long createUser(User user) {
        user.setKarma(0L);
        return userDAO.save(user);
    }

    @Override
    public Optional<User> fetchUser(Long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public void updateUser(Long userId, String newUsername, String newEmail) {
        userDAO.findById(userId).ifPresent(user -> {
            user.setUsername(newUsername);
            user.setEmail(newEmail);
            userDAO.save(user);
        });
    }
}
