package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

case class ValueOtherThanRule[A, B](ruleLookup: RuleLookup[A], thisRuleName: String, forbiddenValues: A*) extends Rule[A] {
  override def apply(cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    if (cursor.isEnd) MatchFailure(cursor, thisRuleName, "end of input")
    else {
      if (forbiddenValues.contains(cursor.value)) {
        MatchFailure(cursor, thisRuleName, s"${cursor.value.value} is forbidden here")
      } else {
        MatchSuccess(cursor.next, ParseTreeLeaf(thisRuleName, cursor.value.value))
      }
    }
  }
}
