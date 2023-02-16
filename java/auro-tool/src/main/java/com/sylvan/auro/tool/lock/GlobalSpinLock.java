package com.sylvan.auro.tool.lock;

public interface GlobalSpinLock {
  void spinLock(String key);

  void spinUnlock(String key);
}
