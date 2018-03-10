package com.yolonews;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.yolonews.auth.*;
import com.yolonews.common.JedisProvider;
import com.yolonews.posts.PostDao;
import com.yolonews.posts.PostDaoRedis;
import com.yolonews.posts.PostService;
import com.yolonews.posts.PostServiceImpl;
import com.yolonews.votes.VoteDao;
import com.yolonews.votes.VoteDaoRedis;
import com.yolonews.votes.VoteService;
import com.yolonews.votes.VoteServiceImpl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author saket.mehta
 */
class YoloNewsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Jedis.class).toProvider(JedisProvider.class);

        bind(AuthDAO.class).to(AuthDaoRedis.class);
        bind(UserDao.class).to(UserDaoRedis.class);
        bind(PostDao.class).to(PostDaoRedis.class);
        bind(VoteDao.class).to(VoteDaoRedis.class);

        bind(AuthService.class).to(AuthServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(VoteService.class).to(VoteServiceImpl.class);
    }

    @Provides
    JedisPool provideJedisPool() {
        return new JedisPool();
    }
}
