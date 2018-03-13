package com.seanshubin.parser.prototype

class DerivedIterator[T](backing: Iterator[T]) extends BaseIterator[T] {
  var count = 0

  override def hasNext: Boolean = backing.hasNext

  override def next(): T = {
    val theNext = backing.next()
    count += 1
    theNext
  }

  def withIterator(f: Iterator[T] => Iterator[T]): BaseIterator[T] = {
    new DerivedIterator[T](f(backing))
  }
}
