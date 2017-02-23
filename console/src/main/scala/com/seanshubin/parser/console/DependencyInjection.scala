package com.seanshubin.parser.console

import java.nio.charset.{Charset, StandardCharsets}

import com.seanshubin.devon.domain.{DevonMarshaller, DevonMarshallerWiring}
import com.seanshubin.parser.domain._

trait DependencyInjection {
  def commandLineArguments: Seq[String]

  lazy val emitLine: String => Unit = println
  lazy val files: FilesContract = FilesDelegate
  lazy val devonMarshaller: DevonMarshaller = DevonMarshallerWiring.Default
  lazy val charset: Charset = StandardCharsets.UTF_8
  lazy val notifications: Notifications = new LineEmittingNotifications(devonMarshaller, emitLine)
  lazy val validateConfiguration: Seq[String] => Either[Seq[String], Configuration] = new ValidateConfiguration(
    files, devonMarshaller, charset)
  lazy val createRunner: Configuration => Runnable = (theConfiguration) => {
    new ConfigurationLifecycle {
      override def configuration: Configuration = theConfiguration
    }.runner
  }
  lazy val runner: Runnable = new ConfigurationLoader(
    commandLineArguments, validateConfiguration, createRunner, notifications)
}
