package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.CalculatorNumber
import com.seanshubin.parser.domain.ParseTree.{ParseTreeBranch, ParseTreeLeaf}

class ParserAssembler extends Assembler[CalculatorToken, CalculatorExpression] {
  override def assemble(parseTree: ParseTree[CalculatorToken]): CalculatorExpression = {
    parseTree.toLines(0).foreach(println)
    parseTree match {
      case ParseTreeBranch("expression", Seq(ParseTreeLeaf("num", CalculatorNumber(value)), ParseTreeBranch("end", Seq()))) =>
        CalculatorExpression.Value(value)
      case _ => throw new RuntimeException(s"Don't know how to assemble $parseTree")
    }
  }
}
