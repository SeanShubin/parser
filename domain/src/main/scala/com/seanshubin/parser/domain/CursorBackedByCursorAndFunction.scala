package com.seanshubin.parser.domain

class CursorBackedByCursorAndFunction[A, B](cursor: Cursor[A], f: A => B) extends Cursor[B] {
  override def next: Cursor[B] = new CursorBackedByCursorAndFunction(cursor.next, f)

  override def value: B = f(cursor.value)

  override def isEnd: Boolean = cursor.isEnd

  override def map[C](f: B => C): Cursor[C] = new CursorBackedByCursorAndFunction[B, C](this, f)

  override def toString: String = cursor.toString
}
