package com.sylvan.hecate.knowledge.properties.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sylvan
 * @date 2020/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestBootstrap.class)
public class ExtensionYamlLoaderTest {
    @Value("${environ.testSwitch}")
    private boolean testSwitch;

    @Value("${environ.testString}")
    private String testString;

    @Value("${environ.testInteger}")
    private int testInteger;

    @Value("${environ.testLong}")
    private long testLong;

    @Test
    public void testExtensionYamlLoaderForSystemProperties() {
        Assert.assertEquals("true", System.getProperty("system.testSwitch"));
        Assert.assertEquals("you", System.getProperty("system.testString"));
    }

    @Test
    public void testExtensionYamlLoaderForEnvironmentProperties() {
        Assert.assertTrue(testSwitch);
        Assert.assertEquals("you", testString);
        Assert.assertEquals(1, testInteger);
        Assert.assertEquals(1234L, testLong);
    }
}
