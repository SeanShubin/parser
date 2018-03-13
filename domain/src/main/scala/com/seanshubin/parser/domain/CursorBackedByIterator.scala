package com.seanshubin.parser.domain

import scala.annotation.tailrec

class CursorBackedByIterator[T](iterator: AnnotatedIterator[T]) extends Cursor[T] {
  private val maybeValue: Option[T] =
    if (iterator.hasNext) Some(iterator.next())
    else None
  private var maybeNext: Option[Cursor[T]] = None

  override def next: Cursor[T] = maybeNext match {
    case Some(nextCursor) => nextCursor
    case None =>
      val nextCursor = new CursorBackedByIterator(iterator)
      maybeNext = Some(nextCursor)
      nextCursor
  }

  override def value: T = maybeValue.get

  override def isEnd: Boolean = maybeValue.isEmpty

  override def column: Int = iterator.column

  override def row: Int = iterator.row

  override def toString: String = {
    val contentString = reifyToEnd.mkString("[", ", ", "]")
    s"$row:$column:$contentString"
  }

  private def reifyToEnd: Seq[T] = {
    reifyToEndRecursive(Nil, this).reverse
  }

  @tailrec
  private def reifyToEndRecursive(soFar: List[T], cursor: Cursor[T]): List[T] = {
    if (cursor.isEnd) soFar
    else reifyToEndRecursive(cursor.value :: soFar, cursor.next)
  }
}