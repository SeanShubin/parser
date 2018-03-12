package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.CalculatorNumber
import com.seanshubin.parser.domain.ParseTree.{ParseTreeBranch, ParseTreeLeaf}

import scala.annotation.tailrec

class ParserAssembler extends Assembler[CalculatorToken, CalculatorExpression] {
  override def assemble(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    assembleExpression(parseTree)
  }

  def assembleExpression(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    parseTree match {
      case ParseTreeBranch("expression", Seq(num, remain)) =>
        val numberExpression = assembleNum(num)
        val expression = assembleRemain(numberExpression, remain)
        expression
    }
  }

  def assembleRemain(calculatorExpression: CalculatorExpression, remain: ParseTree[CalculatorToken]): CalculatorExpression = {
    remain match {
      case ParseTreeBranch("remain", opExpr) =>
        assembleOpExpr(calculatorExpression, opExpr)
    }
  }

  @tailrec
  private def assembleOpExpr(left: CalculatorExpression, opExprSeq: Seq[ParseTree[CalculatorToken]]): CalculatorExpression = {
    opExprSeq match {
      case Nil => left
      case ParseTreeBranch("op-expr", Seq(plus, num)) :: tail =>
        val newLeft = assemblePlus(left, plus, assembleNum(num))
        assembleOpExpr(newLeft, tail)
    }
  }

  def assemblePlus(left: CalculatorExpression, op: ParseTree[CalculatorToken], right: CalculatorExpression): CalculatorExpression = {
    op match {
      case ParseTreeLeaf("plus", CalculatorToken.Plus) =>
        CalculatorExpression.Plus(left, right)
    }
  }

  private def assembleNum(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    parseTree match {
      case ParseTreeLeaf("num", CalculatorNumber(value)) =>
        CalculatorExpression.Value(value)
    }
  }
}
