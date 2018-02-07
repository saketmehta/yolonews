package com.yolonews.common;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface BaseDAO<Entity extends BaseEntity, ID> {
    ID save(Entity entity);

    Optional<Entity> findById(ID id);

    void delete(ID entityId);
}
