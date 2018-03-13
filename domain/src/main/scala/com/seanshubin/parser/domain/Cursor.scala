package com.seanshubin.parser.domain

trait Cursor[T] {
  def next: Cursor[T]

  def value: T

  def isEnd: Boolean

  def column: Int

  def row: Int
}

object Cursor {
  def fromIterator(iterator: Iterator[Char]): Cursor[Char] =
    new CursorBackedByIterator(new AnnotatedCharIterator(iterator))

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
