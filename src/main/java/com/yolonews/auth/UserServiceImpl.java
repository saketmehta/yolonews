package com.yolonews.auth;

import com.google.inject.Inject;

import java.util.Optional;
import java.util.OptionalLong;

/**
 * @author saket.mehta
 */
public class UserServiceImpl implements UserService {
    private final AuthService authService;
    private final UserDao userDAO;

    @Inject
    public UserServiceImpl(AuthService authService, UserDao userDAO) {
        this.authService = authService;
        this.userDAO = userDAO;
    }

    @Override
    public OptionalLong createUser(User user) {
        boolean validUsername = isUsernameValid(user.getUsername());
        boolean validPassword = isPasswordValid(user.getPassword());
        if (validUsername && validPassword) {
            if (userDAO.findByUsername(user.getUsername()).isPresent()) {
                return OptionalLong.empty();
            }
            user.setPassword(authService.hashPassword(user.getPassword()));
            user.setKarma(1L);
            Long userId = userDAO.save(user);
            return OptionalLong.of(userId);
        } else {
            return OptionalLong.empty();
        }
    }

    @Override
    public Optional<User> fetchUser(Long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public void updateUser(Long userId, User user) {
        userDAO.findById(userId).ifPresent(u -> {
            if (isUsernameValid(user.getUsername())) {
                u.setUsername(user.getUsername());
            }
            if (isEmailValid(user.getEmail())) {
                u.setEmail(user.getEmail());
            }
            userDAO.save(u);
        });
    }

    private boolean isUsernameValid(String username) {
        return true;
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    private boolean isEmailValid(String email) {
        return true;
    }
}
