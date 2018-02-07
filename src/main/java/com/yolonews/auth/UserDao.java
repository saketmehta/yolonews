package com.yolonews.auth;

import com.yolonews.common.CrudDao;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface UserDao extends CrudDao<User, Long> {
    Optional<User> findByUsername(String username);
}
