package com.seanshubin.parser.domain

import org.scalatest.FunSuite

import scala.annotation.tailrec

class FilterIteratorTest extends FunSuite {
  test("filter iterator") {
    val iterator = "abc".toIterator
    val filtered = addFilter[Char](iterator, _ != 'b')
    assert(filtered.toSeq.mkString === "ac")
  }

  def addFilter[T](iterator: Iterator[T], accept: T => Boolean): Iterator[T] = {
    new Iterator[T] {
      var lookAhead: Option[T] = advance()

      override def hasNext: Boolean = {
        lookAhead.isDefined
      }

      override def next(): T = {
        val oldValue = lookAhead.get
        lookAhead = advance()
        oldValue
      }

      @tailrec
      private def advance(): Option[T] = {
        if (iterator.hasNext) {
          val value = iterator.next()
          if (accept(value)) {
            Some(value)
          } else {
            advance()
          }
        } else {
          None
        }
      }
    }
  }
}
