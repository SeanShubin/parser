package com.seanshubin.parser.prototype

object EntryPoint extends App {
  val iterator: DerivedIterator[Char] = new DerivedIterator("blah".toIterator)
  val filteredIterator: BaseIterator[Char] = iterator.withIterator { backing =>
    backing.filter(_ != 'a')
  }
  filteredIterator.next()
  filteredIterator.next()
  println(filteredIterator.count)
}
