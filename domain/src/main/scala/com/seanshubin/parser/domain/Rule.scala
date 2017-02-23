package com.seanshubin.parser.domain

sealed trait Rule

object Rule {

  sealed trait Result

  case class Success[A](parseTree: ParseTree[A], cursor: Cursor[A]) extends Result

  case class Failure[A](message: String) extends Result

  class Value[A](matching: A*) extends Rule {
    def apply(cursor: Cursor[A]): Result = {
      if (cursor.isEnd) {
        Failure(s"Got end of input when expecting one of: ${matching.mkString(", ")}")
      } else if (matching.contains(cursor.value)) {
        Success(ParseTree.Leaf[A](cursor.value), cursor)
      } else {
        Failure(s"Got ${cursor.value} when expecting one of: ${matching.mkString(", ")}")
      }
    }
  }

}
