package com.sylvan.hecate.persistence.dao;

import com.sylvan.hecate.persistence.jooq.tables.GlobalLock;
import com.sylvan.hecate.persistence.jooq.tables.pojos.GlobalLockDO;
import com.sylvan.hecate.persistence.jooq.tables.records.GlobalLockRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DAOImpl;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalLockDao extends DAOImpl<GlobalLockRecord, GlobalLockDO, Integer> {
  private final DSLContext context;

  public GlobalLockDao(Configuration configuration, DSLContext context) {
    super(GlobalLock.GLOBAL_LOCK, GlobalLockDO.class, configuration);
    this.context = context;
  }

  @Override
  protected Integer getId(GlobalLockDO globalLockDO) {
    return globalLockDO.getId();
  }

  public boolean keyExists(String key) {
    return context.fetchCount(DSL.selectFrom(getTable()).where(GlobalLock.GLOBAL_LOCK.KEY.eq(key)))
        > 0;
  }

  public boolean exists(String key, String holder) {
    return context.fetchCount(
            DSL.selectFrom(getTable())
                .where(
                    GlobalLock.GLOBAL_LOCK
                        .KEY
                        .eq(key)
                        .and(GlobalLock.GLOBAL_LOCK.HOLDER.eq(holder))
                        .and(
                            GlobalLock.GLOBAL_LOCK
                                .EXPIRE_MS
                                .add(GlobalLock.GLOBAL_LOCK.UPDATED_AT)
                                .gt(System.currentTimeMillis())
                                .or(GlobalLock.GLOBAL_LOCK.EXPIRE_MS.eq(-1L)))))
        > 0;
  }

  public boolean insert(String key, String holder, long expireMs) {
    try {
      return context
              .insertInto(
                  GlobalLock.GLOBAL_LOCK,
                  GlobalLock.GLOBAL_LOCK.KEY,
                  GlobalLock.GLOBAL_LOCK.HOLDER,
                  GlobalLock.GLOBAL_LOCK.EXPIRE_MS)
              .values(key, holder, expireMs)
              .execute()
          == 1;
    } catch (DataAccessException e) {
      log.warn("Failed to insert key: {}", key, e);
      return false;
    }
  }

  public boolean delete(String key, String holder) {
    return context
            .deleteFrom(getTable())
            .where(GlobalLock.GLOBAL_LOCK.KEY.eq(key).and(GlobalLock.GLOBAL_LOCK.HOLDER.eq(holder)))
            .execute()
        == 1;
  }

  public void delete(String key) {
    try {
      context.deleteFrom(getTable()).where(GlobalLock.GLOBAL_LOCK.KEY.eq(key)).execute();
    } catch (DataAccessException e) {
      log.warn("Can't delete key: {}", key, e);
    }
  }

  public boolean update(String key, String holder, long expireMs) {
    try {
      return context
              .update(getTable())
              .set(GlobalLock.GLOBAL_LOCK.EXPIRE_MS, expireMs)
              .where(
                  GlobalLock.GLOBAL_LOCK.KEY.eq(key).and(GlobalLock.GLOBAL_LOCK.HOLDER.eq(holder)))
              .execute()
          == 1;
    } catch (DataAccessException e) {
      log.warn("Failed to update key: {}", key, e);
      return false;
    }
  }

  public boolean upsert(String key, String holder, long expireMs) {
    return context.transactionResult(
        config -> {
          int affectedCount =
              DSL.using(config)
                  .insertInto(
                      GlobalLock.GLOBAL_LOCK,
                      GlobalLock.GLOBAL_LOCK.KEY,
                      GlobalLock.GLOBAL_LOCK.HOLDER,
                      GlobalLock.GLOBAL_LOCK.EXPIRE_MS)
                  .values(key, holder, expireMs)
                  .onDuplicateKeyIgnore()
                  .execute();

          if (affectedCount == 0) {
            // 获取锁失败时尝试获取已经失效的锁
            affectedCount =
                DSL.using(config)
                    .update(GlobalLock.GLOBAL_LOCK)
                    .set(GlobalLock.GLOBAL_LOCK.HOLDER, holder)
                    .set(GlobalLock.GLOBAL_LOCK.EXPIRE_MS, expireMs)
                    .where(
                        GlobalLock.GLOBAL_LOCK
                            .EXPIRE_MS
                            .ne(-1L)
                            .and(
                                GlobalLock.GLOBAL_LOCK
                                    .EXPIRE_MS
                                    .add(GlobalLock.GLOBAL_LOCK.UPDATED_AT)
                                    .lt(System.currentTimeMillis())))
                    .execute();
            return affectedCount == 1;
          }
          return true;
        });
  }
}
