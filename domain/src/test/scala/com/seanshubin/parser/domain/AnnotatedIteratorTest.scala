package com.seanshubin.parser.domain

import org.scalatest.FunSuite

class AnnotatedIteratorTest extends FunSuite {
  test("row and column triple quoted string") {
    val text =
      """ab
        |cd
        |ef
        |""".stripMargin
    val iterator = text.toIterator
    val annotatedIterator = new AnnotatedCharIterator(iterator)
    assert(annotatedIterator.row === 0)
    assert(annotatedIterator.column === 0)

    def verifyNext(expectedChar: Char, expectedRow: Int, expectedColumn: Int): Unit = {
      assert(annotatedIterator.hasNext === true)
      val actualChar = annotatedIterator.next()
      assert(actualChar === expectedChar)
      assert(annotatedIterator.row === expectedRow)
      assert(annotatedIterator.column === expectedColumn)
    }

    verifyNext('a', 0, 1)
    verifyNext('b', 0, 2)
    verifyNext('\n', 1, 0)
    verifyNext('c', 1, 1)
    verifyNext('d', 1, 2)
    verifyNext('\n', 2, 0)
    verifyNext('e', 2, 1)
    verifyNext('f', 2, 2)
    verifyNext('\n', 3, 0)
    assert(annotatedIterator.hasNext === false)
  }

  test("row and column cr") {
    val text = "ab\rcd\ref\r"
    val iterator = text.toIterator
    val annotatedIterator = new AnnotatedCharIterator(iterator)
    assert(annotatedIterator.row === 0)
    assert(annotatedIterator.column === 0)

    def verifyNext(expectedChar: Char, expectedRow: Int, expectedColumn: Int): Unit = {
      assert(annotatedIterator.hasNext === true)
      val actualChar = annotatedIterator.next()
      assert(actualChar === expectedChar)
      assert(annotatedIterator.row === expectedRow)
      assert(annotatedIterator.column === expectedColumn)
    }

    verifyNext('a', 0, 1)
    verifyNext('b', 0, 2)
    verifyNext('\r', 1, 0)
    verifyNext('c', 1, 1)
    verifyNext('d', 1, 2)
    verifyNext('\r', 2, 0)
    verifyNext('e', 2, 1)
    verifyNext('f', 2, 2)
    verifyNext('\r', 3, 0)
    assert(annotatedIterator.hasNext === false)
  }
  test("row and column lf") {
    val text = "ab\ncd\nef\n"
    val iterator = text.toIterator
    val annotatedIterator = new AnnotatedCharIterator(iterator)
    assert(annotatedIterator.row === 0)
    assert(annotatedIterator.column === 0)

    def verifyNext(expectedChar: Char, expectedRow: Int, expectedColumn: Int): Unit = {
      assert(annotatedIterator.hasNext === true)
      val actualChar = annotatedIterator.next()
      assert(actualChar === expectedChar)
      assert(annotatedIterator.row === expectedRow)
      assert(annotatedIterator.column === expectedColumn)
    }

    verifyNext('a', 0, 1)
    verifyNext('b', 0, 2)
    verifyNext('\n', 1, 0)
    verifyNext('c', 1, 1)
    verifyNext('d', 1, 2)
    verifyNext('\n', 2, 0)
    verifyNext('e', 2, 1)
    verifyNext('f', 2, 2)
    verifyNext('\n', 3, 0)
    assert(annotatedIterator.hasNext === false)
  }
  test("row and column crlf") {
    val text = "ab\r\ncd\r\nef\r\n"
    val iterator = text.toIterator
    val annotatedIterator = new AnnotatedCharIterator(iterator)
    assert(annotatedIterator.row === 0)
    assert(annotatedIterator.column === 0)

    def verifyNext(expectedChar: Char, expectedRow: Int, expectedColumn: Int): Unit = {
      assert(annotatedIterator.hasNext === true)
      val actualChar = annotatedIterator.next()
      assert(actualChar === expectedChar)
      assert(annotatedIterator.row === expectedRow)
      assert(annotatedIterator.column === expectedColumn)
    }

    verifyNext('a', 0, 1)
    verifyNext('b', 0, 2)
    verifyNext('\r', 1, 0)
    verifyNext('\n', 1, 0)
    verifyNext('c', 1, 1)
    verifyNext('d', 1, 2)
    verifyNext('\r', 2, 0)
    verifyNext('\n', 2, 0)
    verifyNext('e', 2, 1)
    verifyNext('f', 2, 2)
    verifyNext('\r', 3, 0)
    verifyNext('\n', 3, 0)
    assert(annotatedIterator.hasNext === false)
  }
}
