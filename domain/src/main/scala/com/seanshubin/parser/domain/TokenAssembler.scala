package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.{CalculatorNumber, Whitespace}
import com.seanshubin.parser.domain.ParseTree.{ParseTreeBranch, ParseTreeLeaf}

class TokenAssembler extends Assembler[Char, CalculatorToken] {
  //todo: convert to match
  private val assemblers: Map[String, ParseTree[Char] => CalculatorToken] = Map(
    "number" -> (parseTree => assembleNumber(parseTree)),
    "plus" -> (parseTree => CalculatorToken.Plus),
    "whitespaces" -> (parseTree => Whitespace)
  )

  override def assemble(parseTree: ParseTree[Char]): CalculatorToken = {
    assemblers(parseTree.ruleName)(parseTree)
  }

  private def assembleNumber(parseTree: ParseTree[Char]): CalculatorNumber = {
    val ParseTreeBranch(name, children) = parseTree
    val value = children.map(getCharacter).mkString.toInt
    CalculatorNumber(value)
  }

  private def getCharacter(parseTree: ParseTree[Char]): Char = {
    val ParseTreeLeaf(name, value) = parseTree
    value
  }
}
