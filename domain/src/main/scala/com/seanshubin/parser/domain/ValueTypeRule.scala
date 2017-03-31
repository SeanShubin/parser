package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeLeaf

case class ValueTypeRule[A, B](ruleLookup: RuleLookup[A], thisRuleName: String, valueType: Class[_ <: A]) extends Rule[A] {
  override def apply(cursor: Cursor[A]): MatchResult[A] = {
    if (cursor.isEnd) MatchFailure(thisRuleName, "end of input")
    else {
      if (cursor.value.getClass == valueType) {
        MatchSuccess(ParseTreeLeaf(thisRuleName, cursor.value), cursor.next)
      } else {
        val expectedClass = valueType.getName
        val actualClass = cursor.value.getClass.getName
        MatchFailure(thisRuleName, s"Expected $expectedClass, but got $actualClass instead")
      }
    }
  }
}
