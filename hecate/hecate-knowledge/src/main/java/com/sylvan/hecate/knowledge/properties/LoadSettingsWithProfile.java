package com.sylvan.hecate.knowledge.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoadSettingsWithProfile implements InitializingBean {
  @Value("${greeting.name}")
  private String greetingName;

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("greetingName: " + greetingName);
  }
}
