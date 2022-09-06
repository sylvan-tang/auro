package com.sylvan.juno.common.pubsub.redis;

import org.springframework.data.redis.connection.MessageListener;

public class PubSubUtils {
  /** 监听的 topic */
  public static <T extends MessageListener> String topic(Class<T> clazz) {
    return String.format("redis:listener:%s", clazz.getSimpleName());
  }

  /** 用于持久化 message */
  public static <T extends MessageListener> String persistenceKey(Class<T> clazz) {
    return String.format("redis:listener:persistence:%s", clazz.getSimpleName());
  }
}
