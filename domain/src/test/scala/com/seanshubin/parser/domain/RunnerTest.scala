package com.seanshubin.parser.domain

import java.time.{Clock, Duration}

import com.seanshubin.devon.domain.DevonMarshaller
import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class RunnerTest extends FunSuite {
  test("application flow") {
    val lines = new ArrayBuffer[String]()
    val emitLine: String => Unit = line => lines.append(line)
    val dummyDevonMarshaller: DevonMarshaller = null
    val clock: Clock = new ClockStub(1000, 11000)
    val measureTime: ((=> Unit) => Duration) = new MeasureTime(clock)
    val notifications: Notifications = new LineEmittingNotifications(dummyDevonMarshaller, emitLine)
    val timer: Timer = new TimerImpl(clock, notifications.startTiming, notifications.endTiming)
    val runner: Runnable = new Runner("world", emitLine, timer.trackTime)
    runner.run()
    assert(lines.size === 3)
    assert(lines(0) === "start timer for 'total'")
    assert(lines(1) === "Hello, world!")
    assert(lines(2) === "(10 seconds) total")
  }
}
