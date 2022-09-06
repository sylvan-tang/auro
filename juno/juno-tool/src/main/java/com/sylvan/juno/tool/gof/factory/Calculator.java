package com.sylvan.juno.tool.gof.factory;

import java.util.Objects;

/** @author sylvan */
public enum Calculator implements LongCalculation {
  ADD {
    @Override
    public Long operation(Long val1, Long val2) throws CalculationException {
      if (Objects.isNull(val1) || Objects.isNull(val2)) {
        throw CalculationExceptions.INVALID_PARAM;
      }
      return val1 + val2;
    }
  },
  MINUS {
    @Override
    public Long operation(Long val1, Long val2) throws CalculationException {
      if (Objects.isNull(val1) || Objects.isNull(val2)) {
        throw CalculationExceptions.INVALID_PARAM;
      }
      return val1 - val2;
    }
  },
  MULTIPLY {
    @Override
    public Long operation(Long val1, Long val2) throws CalculationException {
      if (Objects.isNull(val1) || Objects.isNull(val2)) {
        throw CalculationExceptions.INVALID_PARAM;
      }
      return val1 * val2;
    }
  },
  DIV {
    @Override
    public Long operation(Long val1, Long val2) throws CalculationException {
      if (Objects.isNull(val1) || Objects.isNull(val2)) {
        throw CalculationExceptions.INVALID_PARAM;
      }
      if (val2 == 0L) {
        throw CalculationExceptions.ZERO_DIVER;
      }
      return val1 / val2;
    }
  };
}
