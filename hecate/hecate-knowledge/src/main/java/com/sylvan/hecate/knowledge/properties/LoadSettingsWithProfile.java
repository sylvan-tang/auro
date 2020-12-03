package com.sylvan.hecate.knowledge.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** @author sylvan */
@Component
public class LoadSettingsWithProfile implements InitializingBean {
  @Value("${greeting.name}")
  private String greetingName;

  @Value("${greet.word}")
  private String greetingWord;

  @Value("${project.build.directory:null}")
  private String buildDirectory;

  @Override
  public void afterPropertiesSet() {
    System.out.println("Present Project Directory : " + System.getProperty("user.dir"));
    System.out.println(buildDirectory);
    System.out.printf("%s! %s!%n", greetingWord, greetingName);
  }
}
