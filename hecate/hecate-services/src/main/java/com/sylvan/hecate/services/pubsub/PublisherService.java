package com.sylvan.hecate.services.pubsub;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PublisherService {
  private final StringRedisTemplate stringRedisTemplate;

  public void publish(ChannelTopic topic, String message) {
    log.info("Start publish, topic: {}, message: {}", topic.getTopic(), message);
    stringRedisTemplate.convertAndSend(topic.getTopic(), message);
    log.info("End publish, topic: {}, message: {}", topic, message);
  }

  public void batchPublish(ChannelTopic topic, List<String> messages) {
    stringRedisTemplate.executePipelined(
        (RedisConnection redisConnection) -> {
          for (String message : messages) {
            StringRedisConnection conn = (StringRedisConnection) redisConnection;
            conn.publish(topic.getTopic(), message);
          }
          return null;
        });
  }
}
