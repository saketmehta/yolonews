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
        return userDAO.insert(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Override
    public Optional<User> fetchUser(Long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public User updateUser(Long userId, String newUsername, String newEmail) {
        // todo
        return null;
    }
}
