package com.sylvan.hecate.knowledge.gof.factory;

/** @author sylvan */
public interface LongCalculation extends Calculation<Long> {
  Long operation(Long val1, Long val2) throws CalculationException;
}
