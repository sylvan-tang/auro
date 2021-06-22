package com.sylvan.hecate.tool.lock.impl;

import com.sylvan.hecate.persistence.dao.GlobalLockDao;
import com.sylvan.hecate.tool.lock.GlobalLock;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalLockMysqlImpl implements GlobalLock {
  private final GlobalLockDao globalLockDao;

  @Override
  public boolean obtain(String key, String holder, Duration expire) {

    return globalLockDao.insert(key, holder, expire.toMillis())
        || globalLockDao.exists(key, holder)
        || globalLockDao.updateHolder(key, holder, expire.toMillis());
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
