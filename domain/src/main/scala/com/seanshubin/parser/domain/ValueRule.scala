package com.seanshubin.parser.domain

import com.seanshubin.devon.string.StringUtil
import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

case class ValueRule[A](ruleLookup: RuleLookup[A], thisRuleName: String, values: A*) extends Rule[A] {
  override def apply(cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    if (cursor.isEnd) MatchFailure(cursor, thisRuleName, "end of input")
    else {
      if (values.contains(cursor.value.value)) {
        MatchSuccess(cursor.next, ParseTreeLeaf(thisRuleName, cursor.value.value))
      } else {
        val valueString = seqToString(values)
        MatchFailure(cursor, thisRuleName, s"Expected one of: $valueString, but got ${cursor.value.value} instead")
      }
    }
  }

  private def seqToString[A](values: Seq[A]) = values.map(_.toString).map(StringUtil.escape).mkString("\'", "\', \'", "\'")
}
