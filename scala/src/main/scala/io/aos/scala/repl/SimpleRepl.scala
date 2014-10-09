package io.aos.scala.repl

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.ILoop

/**
 * Import java.lang.Math so you can directly run e.g.:
 * abs(-1)
 * cos(10)
 */
object TestConsole extends App {

  val settings = new Settings
  settings.usejavacp.value = true
  settings.deprecation.value = true

  new SampleILoop().process(settings)

}

class SampleILoop extends ILoop {
  override def prompt = "aos> "

  addThunk {
    intp.beQuietDuring {
      intp.addImports("java.lang.Math._")
    }
  }

  override def printWelcome() {
    echo("""\u001B[1;34;49m
       db           ,ad8888ba,     ad88888ba   
      d88b         d8"'    `"8b   d8"     "8b  
     d8'`8b       d8'        `8b  Y8,          
    d8'  `8b      88          88  `Y8aaaaa,    
   d8YaaaaY8b     88          88    `""\"\""8b,  
  d8"\""\""\""\"8b    Y8,        ,8P          `8b  
 d8'        `8b    Y8a.    .a8P   Y8a     a8P  
d8'          `8b    `"Y8888Y"'     "Y88888P"   
\u001B[0m""")
  }

}
