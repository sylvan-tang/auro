package com.sylvan.juno.tool.gof.factory;

/** @author sylvan */
public interface LongCalculation extends Calculation<Long> {
  @Override
  Long operation(Long val1, Long val2) throws CalculationException;
}
