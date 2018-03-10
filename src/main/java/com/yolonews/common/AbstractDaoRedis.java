package com.yolonews.common;

import com.google.common.base.Preconditions;
import com.google.inject.Provider;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author saket.mehta
 */
public abstract class AbstractDaoRedis<Entity extends BaseEntity, ID> implements CrudDao<Entity, ID> {
    private final Provider<Jedis> jedisProvider;

    public AbstractDaoRedis(Provider<Jedis> jedisProvider) {
        this.jedisProvider = jedisProvider;
    }

    @Override
    public ID save(Entity entity) {
        Preconditions.checkNotNull(entity, "Entity is null");
        long now = System.currentTimeMillis();
        return tryWithJedis(jedis -> {
            if (entity.getId() > 0) {
                // update
                entity.setModifiedTime(now);
            } else {
                // create
                entity.setCreatedTime(now);
                entity.setModifiedTime(now);
            }
            return handleSave(jedis, entity);
        });
    }

    @Override
    public Optional<Entity> findById(ID entityId) {
        Preconditions.checkNotNull(entityId, "ID is null");
        return tryWithJedis(jedis -> handleFindById(jedis, entityId));
    }

    @Override
    public void delete(ID entityId) {
        Preconditions.checkNotNull(entityId, "ID is null");
        tryWithJedis(jedis -> handleDelete(jedis, entityId));
    }

    protected <E> E tryWithJedis(Function<Jedis, E> function) {
        try (Jedis jedis = jedisProvider.get()) {
            return function.apply(jedis);
        }
    }

    protected abstract ID handleSave(Jedis jedis, Entity entity);

    protected abstract Optional<Entity> handleFindById(Jedis jedis, ID entityId);

    protected abstract Void handleDelete(Jedis jedis, ID entityId);

    protected abstract Class<Entity> getEntityType();

    protected Entity fromMap(Map<String, String> map) {
        try {
            Entity entity = getEntityType().getDeclaredConstructor().newInstance();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Class<?> curr = getEntityType();
                while (curr != null) {
                    try {
                        Field field = curr.getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        Class<?> fieldType = field.getType();
                        if (fieldType.isAssignableFrom(long.class)) {
                            field.set(entity, Long.parseLong(entry.getValue()));
                        } else {
                            field.set(entity, entry.getValue());
                        }
                        break;
                    } catch (NoSuchFieldException e) {
                        if (curr.getSuperclass() == null) {
                            break;
                        }
                        curr = curr.getSuperclass();
                    }
                }
            }
            return entity;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException("couldn't convert from map", e);
        }
    }

    protected Map<String, String> toMap(Entity entity) {
        Map<String, String> result = new HashMap<>();
        Class<?> aClass = entity.getClass();
        while (aClass != null) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object o = field.get(entity);
                    if (o == null) {
                        continue;
                    }
                    result.put(fieldName, o.toString());
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException("couldn't convert to map", e);
                }
            }
            aClass = aClass.getSuperclass();
        }
        return result;
    }
}
