package com.sylvan.hecate.knowledge.gof.factory;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CalculatorTest {

  @Rule public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testVariableIsNull() throws CalculationException {
    for (Calculator calculator : Calculator.values()) {
      exceptionRule.expect(CalculationException.class);
      exceptionRule.expectMessage(CalculationExceptions.INVALID_PARAM.getMessage());
      calculator.operation(null, 2L);
      exceptionRule.expect(CalculationException.class);
      exceptionRule.expectMessage(CalculationExceptions.INVALID_PARAM.getMessage());
      calculator.operation(1L, null);
    }
  }

  @Test
  public void testDivZero() throws CalculationException {
    exceptionRule.expect(CalculationException.class);
    exceptionRule.expectMessage(CalculationExceptions.ZERO_DIVER.getMessage());
    Calculator.DIV.operation(1L, 0L);
  }

  @Test
  public void testAdd() throws CalculationException {
    Long result = 3L;
    Assert.assertEquals(result, Calculator.ADD.operation(1L, 2L));
  }

  @Test
  public void testMinus() throws CalculationException {
    Long result = -1L;
    Assert.assertEquals(result, Calculator.MINUS.operation(1L, 2L));
  }

  @Test
  public void testMultiply() throws CalculationException {
    Long result = 2L;
    Assert.assertEquals(result, Calculator.MULTIPLY.operation(1L, 2L));
  }

  @Test
  public void testDiv() throws CalculationException {
    Long result = 0L;
    Assert.assertEquals(result, Calculator.DIV.operation(1L, 2L));
    result = 2L;
    Assert.assertEquals(result, Calculator.DIV.operation(2L, 1L));
  }
}
