package com.seanshubin.parser.domain

object RecursiveDescentPrototype extends App {
  sealed trait Token
  case class Number(value:Int) extends Token
  case object Plus extends Token
  case object Minus extends Token
  case object Multiply extends Token
  case object Divide extends Token
  case object OpenParen extends Token
  case object CloseParen extends Token

  val tokens = tokenize(Cursor.fromIterator("1 + 2 * (3 + 4)".toIterator))

  def tokenize(chars:Cursor[Char]):Cursor[Token] = {
    ???
  }

  class MyCharCursor(chars:Cursor[Char]) extends Cursor[Token]{
    override def next: Cursor[Token] = ???

    override def value: Token = ???

    override def isEnd: Boolean = ???
  }
}
