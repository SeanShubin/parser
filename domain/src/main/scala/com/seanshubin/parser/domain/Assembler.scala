package com.seanshubin.parser.domain

trait Assembler[A, B] {
  def assemble(parseTree: ParseTree[A]): B
}
