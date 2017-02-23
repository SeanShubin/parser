package com.seanshubin.parser.domain

class ConfigurationLoader(args: Seq[String],
                          validateConfiguration: Seq[String] => Either[Seq[String], Configuration],
                          createRunner: Configuration => Runnable,
                          notifications: Notifications) extends Runnable {
  override def run(): Unit = {
    val errorOrConfiguration = validateConfiguration(args)
    errorOrConfiguration match {
      case Left(error) => notifications.configurationError(error)
      case Right(configuration) =>
        notifications.effectiveConfiguration(configuration)
        createRunner(configuration).run()
    }
  }
}
