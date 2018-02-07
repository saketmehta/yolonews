package com.yolonews;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.yolonews.auth.*;
import com.yolonews.common.JedisPoolProvider;
import com.yolonews.posts.PostDao;
import com.yolonews.posts.PostDaoRedis;
import com.yolonews.posts.PostService;
import com.yolonews.posts.PostServiceImpl;
import com.yolonews.votes.VoteDao;
import com.yolonews.votes.VoteDaoRedis;
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
        bind(UserDao.class).to(UserDaoRedis.class);
        bind(PostDao.class).to(PostDaoRedis.class);
        bind(VoteDao.class).to(VoteDaoRedis.class);

        bind(AuthService.class).to(AuthServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PostService.class).to(PostServiceImpl.class);
        bind(VoteService.class).to(VoteServiceImpl.class);
    }
}
