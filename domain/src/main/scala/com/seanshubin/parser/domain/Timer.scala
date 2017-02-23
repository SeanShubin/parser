package com.seanshubin.parser.domain

trait Timer {
  def trackTime[T](name: String)(f: => T): T
}
