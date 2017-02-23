package com.seanshubin.parser.console

object ConsoleApplication extends App {
  new DependencyInjection {
    override def commandLineArguments: Seq[String] = args
  }.runner.run()
}
