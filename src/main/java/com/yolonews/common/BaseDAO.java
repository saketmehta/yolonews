package com.yolonews.common;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface BaseDAO<Entity extends BaseEntity, ID> {
    ID insert(Entity entity);

    Optional<Entity> findById(ID id);

    void update(Entity entity);

    void delete(ID entityId);
}
