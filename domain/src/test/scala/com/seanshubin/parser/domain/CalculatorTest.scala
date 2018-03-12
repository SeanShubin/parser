package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.Whitespace
import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import org.scalatest.FunSuite

class CalculatorTest extends FunSuite {
  test("foo") {
    val s = "1 + 2 + 3"
    val tokenIterator = stringToTokenIterator(s)
    val parserRuleLookup = new ParserRuleLookup()
    val assembler = new ParserAssembler()
    val parserIterator = new ParserIterator[CalculatorToken, CalculatorExpression](tokenIterator, parserRuleLookup, assembler, "expression")
    val parsed = parserIterator.toSeq
    parsed.foreach(println)
    parsed.map(_.compute()).foreach(println)
  }

  def stringToTokenIterator(s: String): Iterator[CalculatorToken] = {
    val charIterator = s.toIterator
    val tokenizerRuleLookup = new TokenizerRuleLookup
    val assembler = new TokenAssembler
    val tokenIterator = new ParserIterator[Char, CalculatorToken](charIterator, tokenizerRuleLookup, assembler, "element")
    tokenIterator.filter(_ != Whitespace)
  }

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
