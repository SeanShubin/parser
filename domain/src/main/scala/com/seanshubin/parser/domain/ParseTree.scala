package com.seanshubin.parser.domain

sealed trait ParseTree[A] {
  def ruleName: String

  def toLines(depth: Int): Seq[String]

  def children: Seq[ParseTree[A]]

  def map[B](f: A => B): ParseTree[B]
}

object ParseTree {
  def indent(depth: Int): String = "  " * depth

  //todo: rename to Leaf
  case class ParseTreeLeaf[A](ruleName: String, value: A) extends ParseTree[A] {
    override def toLines(depth: Int): Seq[String] = Seq(s"${indent(depth)}$ruleName $value")

    override def children: Seq[ParseTree[A]] = ???

    override def map[B](f: A => B): ParseTree[B] = ParseTreeLeaf(ruleName, f(value))
  }

  //todo: rename to Branch
  //todo: remove rule name?
  case class ParseTreeBranch[A](ruleName: String, children: Seq[ParseTree[A]]) extends ParseTree[A] {
    override def toLines(depth: Int): Seq[String] = {
      val head = s"${indent(depth)}$ruleName"
      val tail = children.flatMap(_.toLines(depth + 1))
      head +: tail
    }

    override def map[B](f: A => B): ParseTree[B] = ParseTreeBranch(ruleName, children.map(child => child.map(f)))
  }

}
