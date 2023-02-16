package com.sylvan.auro.persistence.codegen;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;

/**
 * @author sylvan
 * @date 2020/12/11
 */
public class GeneratorStrategy extends DefaultGeneratorStrategy {

  private static final String DO_SUFFIX = "DO";

  @Override
  public String getJavaClassName(Definition definition, Mode mode) {
    String className = super.getJavaClassName(definition, mode);
    if (mode == Mode.POJO) {
      return className + DO_SUFFIX;
    }
    return className;
  }
}
