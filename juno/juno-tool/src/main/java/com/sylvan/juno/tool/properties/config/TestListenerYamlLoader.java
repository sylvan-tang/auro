package com.sylvan.juno.tool.properties.config;

import java.util.Map;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/** @author sylvan */
public class TestListenerYamlLoader implements TestExecutionListener {
  private Map<String, String> properties;

  private final String sourcePath;

  public TestListenerYamlLoader(String sourcePath) {
    this.sourcePath = sourcePath;
  }

  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
    properties = YamlLoaderUtils.loadYmlToSystem(sourcePath);
  }

  @Override
  public void afterTestClass(TestContext testContext) throws Exception {
    properties.forEach(
        (key, value) -> {
          System.out.println("Clean " + key + ": " + value);
          System.clearProperty(key);
        });
  }
}
