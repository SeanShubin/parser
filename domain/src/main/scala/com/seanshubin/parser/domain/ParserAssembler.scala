package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.CalculatorNumber
import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

class ParserAssembler extends Assembler[CalculatorToken, CalculatorExpression] {
  override def assemble(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    parseTree.toLines(0).foreach(println)
    parseTree.ruleName match {
      case "expression" => assembleExpression(parseTree)
      case _ => throw new RuntimeException(s"Don't know how to assemble $parseTree")
    }
  }
/*
  private val rules: Seq[Rule[CalculatorToken]] = Seq(
    SequenceRule(this, "expression", "num", "remain"),
    ZeroOrMoreRule(this, "remain", "op-expr"),
    SequenceRule(this, "op-expr", "plus", "num"),
    ValueTypeRule(this, "num", classOf[CalculatorNumber]),
    ValueRule(this, "plus", CalculatorToken.Plus)
  )
 */
  private def assembleExpression(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    val children = parseTree.children
    val num = assembleNum(children(0))
    val remainingExpressionOrEnd = children(1)
    remainingExpressionOrEnd.ruleName match {
      case "remaining-expr" => CalculatorExpression.Plus(num, assembleRemainingExpression(num, remainingExpressionOrEnd))
      case "end" => num
    }
  }

  private def assembleRemainingExpression(current: CalculatorExpression, parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    val children = parseTree.children
    toPlus(children(0))
    val num = assembleNum(children(1))
    val remainingExpressionOrEnd = children(2)
    remainingExpressionOrEnd.ruleName match {
      case "remaining-expr" => CalculatorExpression.Plus(num, assembleRemainingExpression(num, remainingExpressionOrEnd))
      case "end" => num
    }
  }

  private def assembleNum(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    CalculatorExpression.Value(toNum(parseTree).value)
  }

  private def toPlus(target: ParseTree[CalculatorToken]): CalculatorToken = target match {
    case ParseTreeLeaf("plus", CalculatorToken.Plus) => CalculatorToken.Plus
    case _ => throw new RuntimeException("Plus expected")
  }

  private def toNum(target: ParseTree[CalculatorToken]): CalculatorNumber = target match {
    case ParseTreeLeaf("num", CalculatorNumber(value)) => CalculatorNumber(value)
    case _ => throw new RuntimeException("Plus expected")
  }
}
