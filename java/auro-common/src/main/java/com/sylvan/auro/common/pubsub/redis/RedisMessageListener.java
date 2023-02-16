package com.sylvan.auro.common.pubsub.redis;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;

public interface RedisMessageListener extends MessageListener {
  ChannelTopic getTopic();
}
