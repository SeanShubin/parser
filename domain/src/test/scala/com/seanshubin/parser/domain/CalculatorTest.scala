package com.seanshubin.parser.domain

import org.scalatest.FunSuite

class CalculatorTest extends FunSuite {
  test("number") {
    val calculator = createCalculator()
    assert(calculator.evaluate("123") === Right(123))
    assert(calculator.evaluate("456") === Right(456))
    assert(calculator.evaluate("aaa") === Left(s"Unable to convert 'aaa' to a whole number"))
  }

  test("addition") {
    val calculator = createCalculator()
    assert(calculator.evaluate("1+2") === Right(3))
    assert(calculator.evaluate("2+2") === Right(4))
    assert(calculator.evaluate("3+") === Left("number expected"))
  }

  def createCalculator(): Calculator = {
    new Calculator()
  }
}
