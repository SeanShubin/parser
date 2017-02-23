package com.seanshubin.parser.domain

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{LinkOption, Path, Paths}

import com.seanshubin.devon.domain.DevonMarshallerWiring
import org.scalatest.FunSuite

class ValidateConfigurationTest extends FunSuite {
  test("complete configuration") {
    val content =
      """{
        |  greetingTarget world
        |}
        | """.stripMargin

    val expected = Right(Configuration("world"))

    val validateConfiguration = createValidateConfigurationFunction(configFileName = "environment.txt", content = content, exists = true)
    val actual = validateConfiguration(Seq("environment.txt"))
    assert(actual === expected)
  }

  test("missing configuration file") {
    val content =
      """{
        |  servePathOverride gui/src/main/resources/
        |  optionalPathPrefix /template
        |}
        | """.stripMargin

    val expected = Left(Seq("Configuration file named 'environment.txt' not found"))

    val validateConfiguration = createValidateConfigurationFunction(configFileName = "environment.txt", content = content, exists = false)
    val actual = validateConfiguration(Seq("environment.txt"))
    assert(actual === expected)
  }

  test("malformed configuration") {
    val content = "{"

    val expected = Left(Seq("There was a problem reading the configuration file 'environment.txt': Could not match 'element', expected one of: map, array, string, null"))

    val validateConfiguration = createValidateConfigurationFunction(configFileName = "environment.txt", content = content, exists = true)
    val actual = validateConfiguration(Seq("environment.txt"))
    assert(actual === expected)
  }

  def createValidateConfigurationFunction(configFileName: String, content: String, exists: Boolean): Seq[String] => Either[Seq[String], Configuration] = {
    val configFilePath = Paths.get(configFileName)
    val devonMarshaller = DevonMarshallerWiring.Default
    val fileSystem = new FakeFileSystem(configFilePath = configFilePath, content = content, exists = exists)
    val validateConfiguration = new ValidateConfiguration(fileSystem, devonMarshaller, charset)
    validateConfiguration
  }

  val charset: Charset = StandardCharsets.UTF_8

  class FakeFileSystem(configFilePath: Path, content: String, exists: Boolean) extends FilesNotImplemented {
    override def exists(path: Path, options: LinkOption*): Boolean = {
      assert(path === configFilePath)
      exists
    }

    override def readAllBytes(path: Path): Seq[Byte] = {
      assert(path === configFilePath)
      content.getBytes(charset)
    }
  }

}
