package com.yolonews.auth;

import com.google.inject.Inject;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.OptionalLong;

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
        if (validatePassword(username, password)) {
            return userDAO.findByUsername(username).map(user -> {
                SecureRandom random = new SecureRandom();
                String token = new BigInteger(130, random).toString(32);
                authDAO.insertToken(user.getId(), token);
                return token;
            });
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void revokeToken(String token) {
        authDAO.deleteToken(token);
    }

    @Override
    public Optional<User> verifyToken(String token) {
        OptionalLong userId = authDAO.findUserByToken(token);
        if (userId.isPresent()) {
            return userDAO.findById(userId.getAsLong());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean validatePassword(String username, String plaintext) {
        return userDAO.findByUsername(username)
                .map(user -> BCrypt.checkpw(plaintext, user.getPassword()))
                .orElse(false);
    }

    @Override
    public String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
}
