package com.sylvan.hecate.tool.lock.impl;

import com.sylvan.hecate.tool.lock.GlobalLock;
import java.time.Duration;
import org.springframework.stereotype.Component;

@Component
public class GlobalLockMysqlImpl implements GlobalLock {
  @Override
  public boolean obtain(String key, String holder, Duration expire) {
    return false;
  }

  @Override
  public boolean retain(String key, String holder, Duration expire) {
    return false;
  }

  @Override
  public boolean release(String key, String holder) {
    return false;
  }
}
