package com.yolonews.auth;

import com.google.inject.Inject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;

/**
 * @author saket.mehta
 */
public class AuthServiceImpl implements AuthService {
    private final AuthDAO authDAO;
    private final UserDao userDAO;

    @Inject
    public AuthServiceImpl(AuthDAO authDAO, UserDao userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Optional<String> issueToken(String username, String password) {
        return userDAO.findByUsername(username).map(user -> {
            if (user.getPassword().equals(password)) {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                authDAO.insertToken(user.getId(), token);
                return token;
            }
            return null;
        });
    }

    @Override
    public void revokeToken(String token) {
        authDAO.deleteToken(token);
    }

    @Override
    public Optional<User> verifyToken(String token) {
        return authDAO.findUserByToken(token).flatMap(userDAO::findById);
    }
}
