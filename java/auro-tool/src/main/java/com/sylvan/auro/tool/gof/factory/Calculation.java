package com.sylvan.auro.tool.gof.factory;

/** @author sylvan */
public interface Calculation<T> {
  T operation(T val1, T val2) throws CalculationException;
}
