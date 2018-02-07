package com.yolonews.common;

import java.util.Optional;

/**
 * @author saket.mehta
 */
public interface CrudDao<Entity, ID> extends Dao<Entity, ID> {
    ID save(Entity entity);

    Optional<Entity> findById(ID id);

    void delete(ID entityId);
}
