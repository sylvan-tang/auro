package com.sylvan.hecate.tool.gof.factory


import org.junit.Rule
import org.junit.rules.ExpectedException
import spock.lang.Specification
import spock.lang.Unroll

class CalculatorTest extends Specification {

  @Rule public ExpectedException exceptionRule = ExpectedException.none()

  @Unroll
  def "test variable is null for val1 or val2"() {
    when: "校验"
      Calculator.DIV.operation(val1, val2)

    then: "验证"
    def exception = thrown(expectedException)
    exception.message == expectedMessage

    where: "测试数据"
    val1 || val2  || expectedException || expectedMessage
    null || 2L || CalculationException || CalculationExceptions.INVALID_PARAM.message
    1L || null || CalculationException || CalculationExceptions.INVALID_PARAM.message
    1L || 0L || CalculationException || CalculationExceptions.ZERO_DIVER.message
  }

  def "test add"() throws CalculationException {
    when:
    Long result = 3L
    then:
    Calculator.ADD.operation(1L, 2L) == result
  }


  def "test minus"() throws CalculationException {
    when:
    Long result = -1L
    then:
    Calculator.MINUS.operation(1L, 2L) == result
  }


  def "test multiply"() throws CalculationException {
    when:
    Long result = 2L
    then:
    Calculator.MULTIPLY.operation(1L, 2L) == result
  }


  def "test div"() throws CalculationException {
    when:
    Long result = 0L
    then:
    Calculator.DIV.operation(1L, 2L) == result
    when:
    result = 2L
    then:
    Calculator.DIV.operation(2L, 1L) == result
  }
}
