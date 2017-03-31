package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

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
    val iterator = s.toIterator
    val cursor = Cursor.fromIterator(iterator)
    val calculatorRuleLookup = new CalculatorRuleLookup
    val rule = calculatorRuleLookup.lookupRuleByName("expr")
    val matched = rule.apply(cursor)
    ???
    //    matched match {
    //      case MatchSuccess(parseTree, cursor) =>
    //        val assembler = new CalculatorAssembler
    //        val calculated = assembler.assemble(parseTree)
    //        Right(calculated)
    //      case MatchFailure(ruleName, message) =>
    //        Left(message)
    //    }
  }

  def generateParseTree(tokens: Seq[CalculatorToken]): Either[String, ParseTree[CalculatorToken]] = {
    //    Right(ParseTreeLeaf[CalculatorToken]("number", tokens.head.asInstanceOf[CalculatorToken.Number]))
    ???
  }

  def assemble(parseTree: ParseTree[CalculatorToken]): Either[String, Int] = {
    //    val calculatorToken: CalculatorToken = parseTree.asInstanceOf[ParseTreeLeaf[CalculatorToken]].value
    //    val number = calculatorToken.asInstanceOf[CalculatorToken.Number]
    //    val value = number.value
    //    Right(value)
    ???
  }
}