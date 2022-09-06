package com.sylvan.juno.tool.lock;

public interface GlobalSpinLock {
  void spinLock(String key);

  void spinUnlock(String key);
}
