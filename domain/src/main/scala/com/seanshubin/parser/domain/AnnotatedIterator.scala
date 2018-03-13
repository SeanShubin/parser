package com.seanshubin.parser.domain

trait AnnotatedIterator[T] extends Iterator[T] {
  def row: Int

  def column: Int

  def transformBackingIterator(transform: Iterator[T] => Iterator[T]): AnnotatedIterator[T]
}
