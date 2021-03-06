package com.seanshubin.parser.domain

trait Cursor[A] {
  def next: Cursor[A]

  def value: A

  def isEnd: Boolean

  def map[B](f: A => B): Cursor[B]
}

object Cursor {
  def fromIterator[T](iterator: Iterator[T]): Cursor[T] = new CursorBackedByIterator[T](iterator)

  def values[T](begin: Cursor[T], afterEnd: Cursor[T]): Seq[T] = {
    def loop(soFar: List[T], current: Cursor[T]): List[T] = {
      if (current == afterEnd) soFar
      else loop(current.value :: soFar, current.next)
    }

    val reversed = loop(Nil, begin)
    val theValues = reversed.reverse
    theValues
  }
}
