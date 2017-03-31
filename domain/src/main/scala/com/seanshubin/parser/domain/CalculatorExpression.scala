package com.seanshubin.parser.domain

sealed trait CalculatorExpression {
  def compute(): Int
}

object CalculatorExpression {

  case class Value(x: Int) extends CalculatorExpression {
    override def compute(): Int = x
  }

  case class Plus(left: CalculatorExpression, right: CalculatorExpression) extends CalculatorExpression {
    override def compute(): Int = left.compute() + right.compute()
  }

}
