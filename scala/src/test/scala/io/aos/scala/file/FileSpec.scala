package io.aos.scala.file

import org.scalatest._
import scala.io.Source
import java.io.FileNotFoundException
import java.io.IOException
import java.io.FileReader

class FileSpec extends FlatSpec with Matchers {

  "A Source" should "be easy to use" in {

    val fileName = "pom.xml"

    for (line <- Source.fromFile(fileName).getLines()) {
      println(line)
    }

    val fileContents = Source.fromFile(fileName).getLines.mkString

    val fileLines = Source.fromFile(fileName).getLines.toList
    fileLines.foreach(println)

    val filename = "no-such-file.scala"
    try {
      for (line <- Source.fromFile(filename).getLines()) {
        println(line)
      }
    } catch {
      case ex: Exception => println("Bummer, an exception happened.")
    }
    
    val fileName2 = "no-such-file.scala"
    try {
      for (line <- Source.fromFile(fileName2).getLines()) {
        println(line)
      }
    } catch {
      case ex: FileNotFoundException => println("Couldn't find that file.")
      case ex: IOException => println("Had an IOException trying to read that file")
    }

  }

  "A Source" should "be less easy to use" in {
    val reader = new FileReader("pom.xml")
  }

}
