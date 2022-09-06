package com.sylvan.juno.tool.lock.impl;

import com.sylvan.juno.tool.lock.GlobalSpinLock;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalSpinLockRedisImpl implements GlobalSpinLock {
  private static final long TIMEOUT_MS = Duration.ofSeconds(2).toMillis();

  private final GlobalLockMysqlImpl globalLockMysql;

  @Override
  public void spinLock(String key) {
    while (!globalLockMysql.obtain(key, getClass().getSimpleName(), Duration.ofMinutes(1))) {
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void spinUnlock(String key) {
    globalLockMysql.release(key, getClass().getSimpleName());
  }
}
