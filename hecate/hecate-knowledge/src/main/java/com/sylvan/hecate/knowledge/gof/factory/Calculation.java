package com.sylvan.hecate.knowledge.gof.factory;

public interface Calculation<T> {
  T operation(T val1, T val2) throws CalculationException;
}
