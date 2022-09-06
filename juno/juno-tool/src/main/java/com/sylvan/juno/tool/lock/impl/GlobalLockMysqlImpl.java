package com.sylvan.juno.tool.lock.impl;

import com.sylvan.juno.persistence.dao.GlobalLockDao;
import com.sylvan.juno.tool.lock.GlobalLock;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalLockMysqlImpl implements GlobalLock {
  private final GlobalLockDao globalLockDao;

  @Override
  public boolean obtain(String key, String holder, Duration expire) {
    return globalLockDao.upsert(key, holder, expire.toMillis());
  }

  @Override
  public boolean retain(String key, String holder, Duration expire) {
    return globalLockDao.update(key, holder, expire.toMillis());
  }

  @Override
  public boolean release(String key, String holder) {
    return globalLockDao.delete(key, holder);
  }
}
