package com.seanshubin.parser.domain

import java.time.Duration

import org.scalatest.FunSuite

import scala.collection.mutable.ArrayBuffer

class ConfigurationLoaderTest extends FunSuite {
  test("valid configuration") {
    val helper = new Helper(validationResult = Right(Configuration("world")))
    helper.launcher.run()
    assert(helper.sideEffects.size === 2)
    assert(helper.sideEffects(0) ===("notifications.effectiveConfiguration", Configuration("world")))
    assert(helper.sideEffects(1) ===("runner.run", ()))
  }

  test("invalid configuration") {
    val helper = new Helper(validationResult = Left(Seq("error")))
    helper.launcher.run()
    assert(helper.sideEffects.size === 1)
    assert(helper.sideEffects(0) ===("notifications.configurationError", Seq("error")))
  }

  class Helper(validationResult: Either[Seq[String], Configuration]) {
    val sideEffects: ArrayBuffer[(String, Any)] = new ArrayBuffer()
    val validateConfiguration = new FakeValidateConfiguration(Seq("foo.txt"), validationResult)
    val runner = new FakeRunner(sideEffects)
    val createRunner: Configuration => Runnable = (theConfiguration) => runner
    val notifications = new FakeNotification(sideEffects)
    val launcher = new ConfigurationLoader(Seq("foo.txt"), validateConfiguration, createRunner, notifications)
  }

  class FakeValidateConfiguration(expectedArgs: Seq[String], result: Either[Seq[String], Configuration]) extends (Seq[String] => Either[Seq[String], Configuration]) {
    override def apply(args: Seq[String]): Either[Seq[String], Configuration] = {
      assert(args === expectedArgs)
      result
    }
  }

  class FakeNotification(sideEffects: ArrayBuffer[(String, Any)]) extends Notifications {
    def append(name: String, value: Any): Unit = {
      sideEffects.append(("notifications." + name, value))
    }

    override def configurationError(lines: Seq[String]): Unit = append("configurationError", lines)

    override def effectiveConfiguration(configuration: Configuration): Unit = append("effectiveConfiguration", configuration)

    override def topLevelException(exception: Throwable): Unit = append("topLevelException", exception)

    override def startTiming(caption: String): Unit = ???

    override def endTiming(caption: String, duration: Duration): Unit = ???
  }

  class FakeRunner(sideEffects: ArrayBuffer[(String, Any)]) extends Runnable {
    override def run(): Unit = sideEffects.append(("runner.run", ()))
  }

}
