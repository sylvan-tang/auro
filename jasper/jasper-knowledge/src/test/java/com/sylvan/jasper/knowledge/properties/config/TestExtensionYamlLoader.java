package com.sylvan.jasper.knowledge.properties.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TestExtensionYamlLoader extends ExtensionYamlLoader {
  public TestExtensionYamlLoader() {
    super("env-listener-properties", "sys-listener-properties");
    System.out.println("init testExtensionYamlLoader end.");
  }
}
