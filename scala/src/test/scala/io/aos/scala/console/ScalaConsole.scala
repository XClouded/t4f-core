package io.aos.scala.console;

object ScalaConsole {

  def main(args: Array[String]) {
  
    var ok = true
    
    while (ok) {
      print("console> ")
      val ln = readLine()
      ok = ln != null
      if (ok) println(ln + "\n")
    }
  
  }

}
