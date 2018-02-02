package com.yolonews.auth;

import com.yolonews.common.BaseDAO;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface UserDAO extends BaseDAO<User, Long> {
    Optional<User> findByUsername(String username);
}
