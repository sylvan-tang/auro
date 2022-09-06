package com.sylvan.juno.services.pubsub;

import com.sylvan.juno.common.pubsub.redis.RedisMessageListener;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriberFactory implements InitializingBean {
  private final RedisMessageListenerContainer container;

  private final List<RedisMessageListener> listenerList;

  @Override
  public void afterPropertiesSet() {
    listenerList.forEach(this::subscribe);
  }

  private <T extends RedisMessageListener> void subscribe(T listener) {
    container.addMessageListener(listener, listener.getTopic());
  }
}
