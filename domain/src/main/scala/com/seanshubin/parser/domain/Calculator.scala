package com.seanshubin.parser.domain

class Calculator {
  def evaluate(s: String): Either[String, Int] = {
    import Calculator._
    for {
      tokens <- tokenize(s).right
      parseTree <- generateParseTree(tokens).right
      result <- assemble(parseTree).right
    } yield {
      result
    }
  }
}

object Calculator {
  def tokenize(s: String): Either[String, Seq[CalculatorToken]] = {
    try {
      Right(Seq(CalculatorToken.Number(s.toInt)))
    } catch {
      case _: NumberFormatException =>
        Left(s"Unable to convert '$s' to a whole number")
    }
  }

  def generateParseTree(tokens: Seq[CalculatorToken]): Either[String, ParseTree[CalculatorToken]] = {
    Right(ParseTree.Leaf[CalculatorToken](tokens.head.asInstanceOf[CalculatorToken.Number]))
  }

  def assemble(parseTree: ParseTree[CalculatorToken]): Either[String, Int] = {
    val calculatorToken: CalculatorToken = parseTree.asInstanceOf[ParseTree.Leaf[CalculatorToken]].value
    val number = calculatorToken.asInstanceOf[CalculatorToken.Number]
    val value = number.value
    Right(value)
  }
}