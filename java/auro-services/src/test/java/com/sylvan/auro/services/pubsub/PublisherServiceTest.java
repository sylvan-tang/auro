package com.sylvan.auro.services.pubsub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PublisherServiceTest {
  @Autowired private PublisherService publisherService;

  @Autowired private SubscriberFactory subscriberFactory;

  @Autowired private FirstListener firstListener;

  @Autowired private SecondListener secondListener;

  public final ChannelTopic topic = new ChannelTopic("PublisherServiceTest");

  @Test
  void testPublisherAndSubscribe() throws InterruptedException {
    subscriberFactory.afterPropertiesSet();
    publisherService.publish(topic, "first");
    publisherService.publish(topic, "second");
    Thread.sleep(100);
    Assertions.assertEquals(firstListener.msgCount, 2);
    Assertions.assertEquals(secondListener.msgCount, 2);
  }
}
