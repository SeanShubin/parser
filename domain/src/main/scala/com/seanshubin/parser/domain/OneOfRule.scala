package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}

case class OneOfRule[A](ruleLookup: RuleLookup[A], thisRuleName: String, ruleNames: String*) extends Rule[A] {
  override def apply(cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    val successfulMatchFromCursor = successfulMatch(_: String, cursor)
    ruleNames.toStream.map(successfulMatchFromCursor).flatten.headOption match {
      case Some(matchResult) => matchResult
      case None => failure(cursor)
    }
  }

  private def successfulMatch(ruleName: String, cursor: Cursor[RowCol[A]]): Option[MatchSuccess[A]] = {
    val rule = ruleLookup.lookupRuleByName(ruleName)
    val matchResult = rule.apply(cursor)
    matchResult match {
      case x: MatchSuccess[A] => Some(x)
      case x: MatchFailure[A] => None
    }
  }

  private def failure(cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    val ruleNamesString = ruleNames.mkString(", ")
    MatchFailure(cursor, thisRuleName, s"expected one of: $ruleNamesString")
  }
}
