package com.sylvan.hecate.knowledge.terminate;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class TerminateConfiguration {
  @Bean
  public TerminateBean terminateBean() {
    return new TerminateBean();
  }
}
