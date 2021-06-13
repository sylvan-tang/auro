package com.sylvan.hecate.tool.lock;

import java.time.Duration;

public interface GlobalLock {

  /** 获取全局锁 */
  boolean obtain(String key, String holder, Duration expire);

  /** 全局锁续租 */
  boolean retain(String key, String holder, Duration expire);

  /** 释放全局锁 */
  boolean release(String key, String holder);
}
