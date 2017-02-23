package com.seanshubin.parser.domain

import java.time.{Clock, Duration}

class MeasureTime(clock: Clock) extends ((=> Unit) => Duration) {
  override def apply(block: => Unit): Duration = {
    val before = clock.instant()
    block
    val after = clock.instant()
    val duration = Duration.between(before, after)
    duration
  }
}
