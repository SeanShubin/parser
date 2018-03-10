package com.seanshubin.parser.domain

sealed trait MatchResult[A] {
  def success: Boolean

  def cursor: Cursor[A]
}

object MatchResult {
  //todo: add ruleName?
  //todo: begin and end cursor?
  case class MatchSuccess[A](cursor: Cursor[A], parseTree: ParseTree[A]) extends MatchResult[A] {
    override def success: Boolean = true
  }

  //todo: add cursor?
  case class MatchFailure[A](cursor: Cursor[A], ruleName: String, message: String) extends MatchResult[A] {
    override def success: Boolean = false
  }
}

