package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}

class ParserIterator[A, B](backingIterator: AnnotatedIterator[A],
                           ruleLookup: RuleLookup[A],
                           assembler: Assembler[A, B],
                           ruleName: String) extends AnnotatedIterator[B] {
  private var currentCursor: Cursor[A] = new CursorBackedByIterator(backingIterator)
  private var currentMatchResult = parse(ruleName, currentCursor)

  override def hasNext: Boolean = {
    if (currentCursor.isEnd) {
      false
    } else {
      currentMatchResult match {
        case x: MatchSuccess[A] => true
        case MatchFailure(_, rule, message) =>
          throw new RuntimeException(s"Could not match '$rule', $message")
      }
    }
  }

  override def next(): B = {
    currentMatchResult match {
      case MatchSuccess(newCursor, parseTree) =>
        val token = assembler.assemble(parseTree)
        currentCursor = newCursor
        currentMatchResult = parse(ruleName, currentCursor)
        token
      case MatchFailure(_, rule, message) =>
        throw new RuntimeException(s"Could not match '$rule', $message")
    }
  }

  override def row: Int = backingIterator.row

  override def column: Int = backingIterator.column

  override def transformBackingIterator(transform: Iterator[B] => Iterator[B]): AnnotatedIterator[B] = ???

  private def parse(ruleName: String, cursor: Cursor[A]): MatchResult[A] = {
    val rule: Rule[A] = ruleLookup.lookupRuleByName(ruleName)
    val matchResult: MatchResult[A] = rule.apply(cursor)
    matchResult
  }
}
