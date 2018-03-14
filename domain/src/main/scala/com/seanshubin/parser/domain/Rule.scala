package com.seanshubin.parser.domain

trait Rule[A] {
  def thisRuleName: String

  def apply(cursor: Cursor[RowCol[A]]): MatchResult[A]
}
