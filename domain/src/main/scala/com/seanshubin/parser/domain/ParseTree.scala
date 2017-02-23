package com.seanshubin.parser.domain

sealed trait ParseTree[A]

object ParseTree {

  case class Leaf[A](value: A) extends ParseTree[A]

  case class Branch[A](children: Seq[ParseTree[A]]) extends ParseTree[A]

}
