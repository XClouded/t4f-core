package io.aos.scala.loop

import org.specs.matcher.Matchers
import org.scalatest.FlatSpec

class LoopTest extends FlatSpec with Matchers {

  "Loop" should "be easy to write and read" in {
    
    val ns = List("1", "2eé")
    for (n <- ns) {
      println(n)
    }
    
  }

}
