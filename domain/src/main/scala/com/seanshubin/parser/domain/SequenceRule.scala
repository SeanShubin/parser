package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}
import com.seanshubin.parser.domain.ParseTree.ParseTreeBranch

case class SequenceRule[A](ruleLookup: RuleLookup[A], thisRuleName: String, ruleNames: String*) extends Rule[A] {
  override def apply(cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    def loop(soFar: List[MatchSuccess[A]], remaining: List[String], cursor: Cursor[RowCol[A]]): List[MatchResult[A]] = {
      if (remaining.isEmpty) soFar
      else {
        val rule = ruleLookup.lookupRuleByName(remaining.head)
        val matchResult = rule.apply(cursor)
        matchResult match {
          case x: MatchSuccess[A] => loop(x :: soFar, remaining.tail, x.cursor)
          case x: MatchFailure[A] => List(x)
        }
      }
    }

    val allResults = loop(Nil, ruleNames.toList, cursor)
    allResults.head match {
      case MatchSuccess(newCursor, _) =>
        val children = allResults.flatMap {
          case MatchSuccess(_, parseTree) => Some(parseTree)
          case _ => None
        }
        MatchSuccess(newCursor, ParseTreeBranch(thisRuleName, children.reverse))
      case x: MatchFailure[A] =>
        val sequenceString = ruleNames.mkString(", ")
        MatchFailure(cursor, thisRuleName, s"expected sequence: $sequenceString")
    }
  }
}
