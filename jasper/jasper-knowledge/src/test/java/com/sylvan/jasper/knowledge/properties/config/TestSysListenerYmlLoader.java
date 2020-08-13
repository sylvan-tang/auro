package com.sylvan.jasper.knowledge.properties.config;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class TestSysListenerYmlLoader implements TestExecutionListener {

  @Override
  public void beforeTestClass(TestContext testContext) throws Exception {
    YamlLoaderUtils.loadYmlToSystem("sys-listener-properties");
  }
}
