package com.seanshubin.parser.domain

sealed trait ParseTree[A]

object ParseTree {
  //todo: rename to Leaf
  case class ParseTreeLeaf[A](ruleName:String, value: A) extends ParseTree[A]


  //todo: rename to Branch
  //todo: remove rule name?
  case class ParseTreeBranch[A](ruleName:String, children: Seq[ParseTree[A]]) extends ParseTree[A]
}
