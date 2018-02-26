package com.yolonews.auth;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface AuthService {
    Optional<String> issueToken(String username, String password);

    void revokeToken(String token);

    Optional<User> verifyToken(String token);

    boolean validatePassword(String username, String password);

    String hashPassword(String plaintext);
}
