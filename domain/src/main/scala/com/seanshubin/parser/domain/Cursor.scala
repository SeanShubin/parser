package com.seanshubin.parser.domain

import scala.annotation.tailrec

trait Cursor[T] {
  def next: Cursor[T]

  def value: T

  def isEnd: Boolean

  def column: Int

  def row: Int
}

object Cursor {
  def fromIterator[T](iterator: Iterator[T]): Cursor[T] =
    new CursorBackedByIterator[T](iterator, (x: T) => false, 0, 0)

  def fromIterator[T](iterator: Iterator[T], endOfRowFunction: T => Boolean): Cursor[T] =
    new CursorBackedByIterator[T](iterator, endOfRowFunction, 0, 0)

  def values[T](begin: Cursor[T], afterEnd: Cursor[T]): Seq[T] = {
    def loop(soFar: List[T], current: Cursor[T]): List[T] = {
      if (current == afterEnd) soFar
      else loop(current.value :: soFar, current.next)
    }

    val reversed = loop(Nil, begin)
    val theValues = reversed.reverse
    theValues
  }

  private class CursorBackedByIterator[T](iterator: Iterator[T],
                                          val endOfRowFunction: T => Boolean,
                                          val row: Int,
                                          val column: Int) extends Cursor[T] {
    private val maybeValue: Option[T] =
      if (iterator.hasNext) Some(iterator.next())
      else None
    private var maybeNext: Option[Cursor[T]] = None

    override def next: Cursor[T] = maybeNext match {
      case Some(nextCursor) => nextCursor
      case None =>
        val newRow = if (endOfRowFunction(value)) row + 1 else row
        val nextCursor = new CursorBackedByIterator(iterator, endOfRowFunction, newRow, column + 1)
        maybeNext = Some(nextCursor)
        nextCursor
    }

    override def value: T = maybeValue.get

    override def isEnd: Boolean = maybeValue.isEmpty

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

}
