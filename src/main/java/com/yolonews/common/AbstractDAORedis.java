package com.yolonews.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author saket.mehta
 */
public abstract class AbstractDAORedis<Entity extends BaseEntity & Mappable, ID> implements BaseDAO<Entity, ID> {
    private final JedisPool jedisPool;

    protected AbstractDAORedis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public ID insert(Entity entity) {
        return tryWithJedis(jedis -> handleInsert(jedis, entity));
    }

    @Override
    public Optional<Entity> findById(ID entityId) {
        return tryWithJedis(jedis -> handleFindById(jedis, entityId));
    }

    @Override
    public void update(Entity entity) {
        tryWithJedis(jedis -> handleUpdate(jedis, entity));
    }

    @Override
    public void delete(ID entityId) {
        tryWithJedis(jedis -> handleDelete(jedis, entityId));
    }

    protected abstract ID handleInsert(Jedis jedis, Entity entity);

    protected abstract Optional<Entity> handleFindById(Jedis jedis, ID entityId);

    protected abstract Void handleUpdate(Jedis jedis, Entity entity);

    protected abstract Void handleDelete(Jedis jedis, ID entityId);

    protected <E> E tryWithJedis(Function<Jedis, E> function) {
        try (Jedis jedis = jedisPool.getResource()) {
            return function.apply(jedis);
        }
    }
}
