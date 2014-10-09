package io.aos.scala.repl

import scala.io.Source._
import java.io.File

object Imports {

  def asList = classPathImports

  def path(dir: String = "") = dir + "scala-console-imports"

  def classPathImports =
    List(
      path(),
      path("META-INF/")
    ).flatMap(x => Option(getClass.getClassLoader.getResource(x)))
      .headOption
      .fold(fileSystemImports)(url => fromURL(url).getLines().toList)

  def fileSystemImports =
    List(
        path(),
        path("bin/"),
        path("src/main/resources/"),
        path("src/main/resources/META-INF/"),
        path("../src/main/resources/"),
        path("../src/main/resources/META-INF/")
    ).map(f => new File(f))
      .find(_.exists)
      .fold(defaultImports)(file => fromFile(file).getLines().toList)

  def defaultImports = List(
    "java.lang.Math._"
  )

}