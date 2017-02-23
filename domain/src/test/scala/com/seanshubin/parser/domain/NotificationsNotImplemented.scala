package com.seanshubin.parser.domain

import java.time.Duration

abstract class NotificationsNotImplemented extends Notifications {
  override def effectiveConfiguration(configuration: Configuration): Unit = ???

  override def startTiming(caption: String): Unit = ???

  override def endTiming(caption: String, duration: Duration): Unit = ???

  override def configurationError(lines: Seq[String]): Unit = ???

  override def topLevelException(exception: Throwable): Unit = ???
}
