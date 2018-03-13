package com.seanshubin.parser.prototype

trait BaseIterator[T] extends Iterator[T] {
  def count: Int

  def withIterator(f: Iterator[T] => Iterator[T]): BaseIterator[T]
}
