package com.seanshubin.parser.domain

sealed trait MatchResult[A] {
  def success: Boolean
}

object MatchResult {
  //todo: add ruleName?
  //todo: begin and end cursor?
  case class MatchSuccess[A](parseTree: ParseTree[A], cursor: Cursor[A]) extends MatchResult[A] {
    override def success: Boolean = true
  }

  //todo: add cursor?
  case class MatchFailure[A](ruleName: String, message: String) extends MatchResult[A] {
    override def success: Boolean = false
  }
}

