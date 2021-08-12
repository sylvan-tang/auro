package com.sylvan.hecate.tool.lock.impl;

import com.sylvan.hecate.tool.lock.GlobalSpinLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalSpinLockRedisImpl implements GlobalSpinLock {
  @Override
  public void spinLock(String key) {}

  @Override
  public void spinUnlock(String key) {}
}
