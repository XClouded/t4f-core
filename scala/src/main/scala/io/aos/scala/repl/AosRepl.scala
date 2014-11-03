package io.aos.scala.repl

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.ILoop
import scala.tools.nsc.interpreter.ReplReporter

/**
 * http://www.scala-lang.org/archives/downloads/distrib/files/nightly/docs/compiler/scala/tools/nsc/interpreter/package.html
 * http://www.michaelpollmeier.com/create-your-custom-scala-repl
 */
object Console extends App {
  val settings = new Settings
  settings.usejavacp.value = true
  settings.deprecation.value = true
  new AosILoop().process(settings)
}

class AosILoop extends ILoop {

  override def prompt = "aos> "

  var aosIntp: AosInterpreter = _
  
  addThunk {
    intp.beQuietDuring {
      intp.addImports(Imports.asList: _*)
    }
  }

  override def printWelcome() {
    echo("""
 _____ _____ _____ 
|  _  |     |   __|
|     |  |  |__   |
|__|__|_____|_____|

""")
  }

  override def createInterpreter() {
    if (addedClasspath != "")
      settings.classpath.append(addedClasspath)
    aosIntp = new AosInterpreter
    intp = aosIntp
  }

  override def command(line: String): Result = {
    val result = super.command(line)
//    if (result.keepRunning && result.lineToRecord.isDefined)
//      printLastValue
    result
  }

  /** Prints the last value by expanding its elements if it's iterator-like or collection-like. */
  def printLastValue = aosIntp.lastValue match {
    case Some(value) ⇒ for (v ← toIterator(value)) out.println("==> " + v)
    case _           ⇒
  }

  /**Coerces the specified value into an iterator. */
  def toIterator(value: Any): Iterator[Any] = {
    import scala.collection.JavaConverters._
    value match {
      case t: Traversable[Any]      ⇒ t.toIterator
      case a: Array[_]              ⇒ a.toIterator
      case i: java.lang.Iterable[_] ⇒ i.asScala.toIterator
      case i: java.util.Iterator[_] ⇒ i.asScala
      case m: java.util.Map[_, _]   ⇒ m.asScala.toIterator
      case _                        ⇒ Iterator.single(value)
    }
  }

  class AosInterpreter extends ILoopInterpreter {
    def prevRequest: Option[Request] = Option(lastRequest)

    /**
     * Returns the last value evaluated by this interpreter.
     * See https://issues.scala-lang.org/browse/SI-4899 
     **/
    def lastValue: Option[AnyRef] = prevRequest flatMap (_.lineRep.callOpt("$result"))
    
  }

}
