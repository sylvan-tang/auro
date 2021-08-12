package com.sylvan.hecate.tool.lock;

public interface GlobalSpinLock {
  void spinLock(String key);

  void spinUnlock(String key);
}
