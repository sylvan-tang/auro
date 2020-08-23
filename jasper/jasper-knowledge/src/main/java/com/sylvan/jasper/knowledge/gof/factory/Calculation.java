package com.sylvan.jasper.knowledge.gof.factory;

public interface Calculation<T> {
  T operation(T val1, T val2) throws CalculationException;
}
