package com.sylvan.hecate.knowledge.gof.factory;

public class CalculationExceptions {
  public static final CalculationException ZERO_DIVER = new CalculationException("除数不能为零");
  public static final CalculationException INVALID_PARAM =
      new CalculationException("不能对 null 进行数学运算");
}
