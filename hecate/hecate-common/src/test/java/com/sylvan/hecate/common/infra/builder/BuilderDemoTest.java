package com.sylvan.hecate.common.infra.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author sylvan
 * @date 2020/11/30
 */
public class BuilderDemoTest {
  @Test
  public void testDefaultBuilder() {
    BuilderDemo.BuilderDemoBuilder builder = BuilderDemo.builder();
    builder.name("too");
    BuilderDemo demo = builder.build();
    Assert.assertEquals("too", demo.getName());

    // build 方法被 override，original 被改写为 false
    Assert.assertFalse(demo.isOriginal());
  }

  @Test
  public void testBuilder() {
    BuilderDemo.BuilderDemoBuilder builder = BuilderDemo.builder();
    BuilderDemo demo = builder.build();
    Assert.assertEquals("foo", demo.getName());

    // build 方法被 override，original 被改写为 false
    Assert.assertFalse(demo.isOriginal());
  }
}
