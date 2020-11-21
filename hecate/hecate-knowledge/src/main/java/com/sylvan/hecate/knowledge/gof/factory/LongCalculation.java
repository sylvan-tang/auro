package com.sylvan.hecate.knowledge.gof.factory;

public interface LongCalculation extends Calculation<Long> {
    Long operation(Long val1, Long val2) throws CalculationException;
}
