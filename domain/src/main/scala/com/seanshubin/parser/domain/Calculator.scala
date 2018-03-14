package com.seanshubin.parser.domain

class Calculator {
  def evaluate(s: String): Either[String, Int] = {
    import Calculator._
    val charIterator = s.toIterator
    val rowColIterator = RowColIterator.fromCharIterator(charIterator)
    val tokenIterator = new ParserIterator[Char, CalculatorToken](rowColIterator, tokenizerRuleLookup, tokenAssembler, "element")
    //      val filteredTokenIterator:ParserIterator[Char, CalculatorToken] = tokenIterator.filter(_ != Whitespace)
    val parserIterator = new ParserIterator[CalculatorToken, CalculatorExpression](tokenIterator, parserRuleLookup, parserAssembler, "expression")
    val parsed = parserIterator.toIndexedSeq
    Right(parsed.map(_.value).head.compute())
  }
}

object Calculator {
  val tokenizerRuleLookup = new TokenizerRuleLookup
  val tokenAssembler = new TokenAssembler
  val parserRuleLookup = new ParserRuleLookup
  val parserAssembler = new ParserAssembler
}