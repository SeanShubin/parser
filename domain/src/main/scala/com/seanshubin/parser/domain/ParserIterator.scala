package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.MatchResult.{MatchFailure, MatchSuccess}

class ParserIterator[A, B](backingIterator: Iterator[RowCol[A]],
                           ruleLookup: RuleLookup[A],
                           assembler: Assembler[A, B],
                           ruleName: String) extends Iterator[RowCol[B]] {
  private var currentCursor: Cursor[RowCol[A]] = Cursor.fromIterator(backingIterator)
  private var currentMatchResult = parse(ruleName, currentCursor)

  override def hasNext: Boolean = {
    if (currentCursor.isEnd) {
      false
    } else {
      currentMatchResult match {
        case x: MatchSuccess[RowCol[A]] => true
        case MatchFailure(cursor, rule, message) =>
          throwError(cursor, rule, message)
      }
    }
  }

  override def next(): RowCol[B] = {
    currentMatchResult match {
      case MatchSuccess(newCursor, parseTree) =>
        val token = assembler.assemble(parseTree)
        currentCursor = newCursor
        currentMatchResult = parse(ruleName, currentCursor)
        RowCol(token, newCursor.value.row, newCursor.value.column)
      case MatchFailure(cursor, rule, message) =>
        throwError(cursor, rule, message)
    }
  }

  private def parse(ruleName: String, cursor: Cursor[RowCol[A]]): MatchResult[A] = {
    val rule: Rule[A] = ruleLookup.lookupRuleByName(ruleName)
    val matchResult: MatchResult[A] = rule.apply(cursor)
    matchResult
  }

  private def throwError(cursor: Cursor[RowCol[A]], rule: String, message: String) = {
    val row = cursor.value.row + 1
    val column = cursor.value.column + 1
    val cursorString = cursor.map(_.value).toString
    throw new RuntimeException(s"Error($row:$column): Could not match '$rule', $message, $cursorString")
  }
}
