package com.seanshubin.parser.domain

sealed trait CalculatorToken

object CalculatorToken {

  case class CalculatorNumber(value: Int) extends CalculatorToken {
    override def toString: String = value.toString
  }

  object Plus extends CalculatorToken {
    override def toString: String = "Plus"
  }

  object Whitespace extends CalculatorToken {
    override def toString: String = "Whitespace"
  }

  val NoWhitespaceFilter: CalculatorToken => Boolean = token => token != Whitespace
}
