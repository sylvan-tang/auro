package com.sylvan.hecate.services.pubsub;

import com.sylvan.hecate.common.pubsub.redis.RedisMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecondListener implements RedisMessageListener {
  public int msgCount = 0;

  public final ChannelTopic topic = new ChannelTopic("PublisherServiceTest");

  @Override
  public void onMessage(Message message, byte[] bytes) {
    msgCount += 1;
  }

  @Override
  public ChannelTopic getTopic() {
    return topic;
  }
}
