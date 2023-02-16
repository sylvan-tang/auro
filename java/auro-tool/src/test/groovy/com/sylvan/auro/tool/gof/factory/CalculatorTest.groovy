package com.sylvan.auro.tool.gof.factory


import spock.lang.Specification
import spock.lang.Unroll

class CalculatorTest extends Specification {

    @Unroll
    def "test variable is null for val1 or val2"() {
        when: "校验"
        Calculator.DIV.operation(val1, val2)

        then: "验证"
        def exception = thrown(expectedException)
        exception.message == expectedMessage

        where: "测试数据"
        val1 || val2 || expectedException    || expectedMessage
        null || 2L   || CalculationException || CalculationExceptions.INVALID_PARAM.message
        1L   || null || CalculationException || CalculationExceptions.INVALID_PARAM.message
        1L   || 0L   || CalculationException || CalculationExceptions.ZERO_DIVER.message
    }

    def "test calculation for all"() {
        when: "校验"
        Long result = calculator.operation(val1, val2)
        then: "校验"
        result == expectedResult
        where: "测试数据"
        calculator          || val1 || val2 || expectedResult
        Calculator.ADD      || 1L   || 2L   || 3L
        Calculator.MINUS    || 1L   || 2L   || -1L
        Calculator.MULTIPLY || 1L   || 2L   || 2L
        Calculator.DIV      || 1L   || 2L   || 0L
        Calculator.DIV      || 2L   || 1L   || 2L
    }


}
