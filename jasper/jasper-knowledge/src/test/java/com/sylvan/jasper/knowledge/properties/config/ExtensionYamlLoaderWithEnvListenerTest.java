package com.sylvan.jasper.knowledge.properties.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(value = TestSysListenerYmlLoader.class)
public class ExtensionYamlLoaderWithEnvListenerTest {

  @Test
  public void testExtensionYamlLoaderForSystemProperties() {
    Assert.assertEquals("true", System.getProperty("sys.listener.testSwitch"));
    Assert.assertEquals("you", System.getProperty("sys.listener.testString"));
  }
}
