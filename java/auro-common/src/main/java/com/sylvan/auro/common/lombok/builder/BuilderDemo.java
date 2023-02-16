package com.sylvan.auro.common.lombok.builder;

import lombok.Builder;
import lombok.Getter;

/**
 * @author sylvan
 * @date 2020/11/30
 */
@Builder(toBuilder = true)
@Getter
public class BuilderDemo {
  // 使用 @Builder.Default 才能使默认值生效
  @Builder.Default private String name = "foo";

  // 使用 @Builder.Default 才能使默认值生效
  @Builder.Default private boolean original = true;

  public static BuilderDemoBuilder builder() {
    return new BuilderDemoBuilder() {
      @Override
      public BuilderDemo build() {
        BuilderDemo demo = super.build();
        demo.original = false;
        return demo;
      }
    };
  }
}
