package com.yolonews;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.yolonews.auth.*;
import com.yolonews.common.JedisPoolProvider;
import com.yolonews.posts.PostDAO;
import com.yolonews.posts.PostDAORedis;
import com.yolonews.posts.PostService;
import com.yolonews.posts.PostServiceImpl;
import com.yolonews.votes.VoteDAO;
import com.yolonews.votes.VoteDAORedis;
import com.yolonews.votes.VoteService;
import com.yolonews.votes.VoteServiceImpl;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
class YoloNewsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JedisPool.class).toProvider(JedisPoolProvider.class).in(Singleton.class);

        bind(AuthDAO.class).to(AuthDAORedis.class);
        bind(UserDAO.class).to(UserDAORedis.class);
        bind(PostDAO.class).to(PostDAORedis.class);
        bind(VoteDAO.class).to(VoteDAORedis.class);

        bind(AuthService.class).to(AuthServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(VoteService.class).to(VoteServiceImpl.class);
    }
}
