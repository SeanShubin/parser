package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

case class ValueOtherThanRule[A, B](ruleLookup: RuleLookup[A], thisRuleName: String, forbiddenValues: A*) extends Rule[A] {
  override def apply(cursor: Cursor[A]): MatchResult[A] = {
    if (cursor.isEnd) MatchFailure(thisRuleName, "end of input")
    else {
      if (forbiddenValues.contains(cursor.value)) {
        MatchFailure(thisRuleName, s"${cursor.value} is forbidden here")
      } else {
        MatchSuccess(ParseTreeLeaf(thisRuleName, cursor.value), cursor.next)
      }
    }
  }
}
