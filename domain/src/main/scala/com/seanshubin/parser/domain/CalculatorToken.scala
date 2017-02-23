package com.seanshubin.parser.domain

sealed trait CalculatorToken

object CalculatorToken {

  case class Number(value: Int) extends CalculatorToken

  object Plus extends CalculatorToken

}
