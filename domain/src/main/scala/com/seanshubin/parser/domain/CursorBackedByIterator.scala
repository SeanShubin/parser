package com.seanshubin.parser.domain

import scala.annotation.tailrec

class CursorBackedByIterator[A](iterator: Iterator[A]) extends Cursor[A] {
  private val maybeValue: Option[A] =
    if (iterator.hasNext) Some(iterator.next())
    else None
  private var maybeNext: Option[Cursor[A]] = None

  override def next: Cursor[A] = maybeNext match {
    case Some(nextCursor) => nextCursor
    case None =>
      val nextCursor = Cursor.fromIterator(iterator)
      maybeNext = Some(nextCursor)
      nextCursor
  }

  override def value: A = maybeValue.get

  override def isEnd: Boolean = maybeValue.isEmpty

  override def map[B](f: A => B): Cursor[B] = {
    new CursorBackedByCursorAndFunction[A, B](this, f)
  }

  override def toString: String = {
    val contentString = reifyToEnd.mkString("[", ", ", "]")
    contentString
  }

  private def reifyToEnd: Seq[A] = {
    reifyToEndRecursive(Nil, this).reverse
  }

  @tailrec
  private def reifyToEndRecursive(soFar: List[A], cursor: Cursor[A]): List[A] = {
    if (cursor.isEnd) soFar
    else reifyToEndRecursive(cursor.value :: soFar, cursor.next)
  }
}
