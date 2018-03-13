package com.seanshubin.parser.domain

class AnnotatedCharIterator(iterator: Iterator[Char]) extends AnnotatedIterator[Char] {
  private var lastCharWasCr = false
  var row: Int = 0
  var column: Int = 0

  override def hasNext: Boolean = iterator.hasNext

  override def next(): Char = {
    val nextValue = iterator.next()
    if (lastCharWasCr) {
      if (nextValue == '\r') {
        row += 1
      } else if (nextValue == '\n') {
        lastCharWasCr = false
      } else {
        lastCharWasCr = false
        column += 1
      }
    } else {
      if (nextValue == '\r') {
        lastCharWasCr = true
        column = 0
        row += 1
      } else if (nextValue == '\n') {
        column = 0
        row += 1
      } else {
        column += 1
      }
    }
    nextValue
  }

  override def transformBackingIterator(transform: Iterator[Char] => Iterator[Char]): AnnotatedIterator[Char] = ???
}
