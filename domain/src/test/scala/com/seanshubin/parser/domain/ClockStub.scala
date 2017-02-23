package com.seanshubin.parser.domain

import java.time.{Clock, Instant, ZoneId}

class ClockStub(values: Long*) extends Clock {
  private var valueIndex = 0

  override def getZone: ZoneId = ???

  override def instant(): Instant = {
    val result = Instant.ofEpochMilli(values(valueIndex))
    valueIndex += 1
    result
  }

  override def withZone(zone: ZoneId): Clock = ???
}
