package com.seanshubin.parser.domain

import org.scalatest.FunSuite

class CursorTest extends FunSuite {
  test("from iterator") {
    val text = "abc"
    val iterator = text.iterator
    val cursorA = Cursor.fromIterator(iterator)
    val cursorB = cursorA.next
    val cursorC = cursorB.next
    val cursorEnd = cursorC.next
    assert(cursorA.value === 'a')
    assert(cursorA.isEnd === false)
    assert(cursorB.value === 'b')
    assert(cursorB.isEnd === false)
    assert(cursorC.value === 'c')
    assert(cursorC.isEnd === false)
    assert(cursorEnd.isEnd === true)
  }

  test("split cursors iterator") {
    val text = "abc"
    val iterator = text.iterator
    val cursorA = Cursor.fromIterator(iterator)
    val cursorB = cursorA.next
    val cursorC = cursorA.next
    assert(cursorA.value === 'a')
    assert(cursorA.isEnd === false)
    assert(cursorB.value === 'b')
    assert(cursorB.isEnd === false)
    assert(cursorC.value === 'b')
    assert(cursorC.isEnd === false)
  }

  test("compute between") {
    val text = "abcde"
    val iterator = text.iterator
    val cursorA = Cursor.fromIterator(iterator)
    val cursorB = cursorA.next
    val cursorE = cursorB.next.next.next
    val actual = Cursor.values(cursorB, cursorE)
    val expected = Seq('b', 'c', 'd')
    assert(actual === expected)
  }

  test("column") {
    val text = "abc"
    val iterator = text.iterator
    val cursorA = Cursor.fromIterator(iterator)
    val cursorB = cursorA.next
    val cursorC = cursorB.next
    val cursorEnd = cursorC.next
    assert(cursorA.column === 0)
    assert(cursorB.column === 1)
    assert(cursorC.column === 2)
    assert(cursorEnd.column === 3)
  }

  test("row") {
    val text = "abc\ndef"
    val iterator = text.iterator
    val cursorA = Cursor.fromIterator(iterator)
    val cursorB = cursorA.next
    val cursorC = cursorB.next
    val cursorNewline = cursorC.next
    val cursorD = cursorNewline.next
    val cursorE = cursorD.next
    val cursorF = cursorE.next
    val cursorEnd = cursorF.next
    assert(cursorA.row === 0)
    assert(cursorB.row === 0)
    assert(cursorC.row === 0)
    assert(cursorNewline.row === 0)
    assert(cursorD.row === 1)
    assert(cursorE.row === 1)
    assert(cursorF.row === 1)
    assert(cursorEnd.row === 1)
  }
}
