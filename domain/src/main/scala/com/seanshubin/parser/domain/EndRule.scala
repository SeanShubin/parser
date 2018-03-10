package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeBranch

case class EndRule[A](ruleLookup: RuleLookup[A], thisRuleName: String) extends Rule[A] {
  override def apply(cursor: Cursor[A]): MatchResult[A] = {
    if (cursor.isEnd) MatchSuccess(cursor, ParseTreeBranch(thisRuleName, Nil))
    else MatchFailure(cursor, thisRuleName, "end of input expected")
  }
}
