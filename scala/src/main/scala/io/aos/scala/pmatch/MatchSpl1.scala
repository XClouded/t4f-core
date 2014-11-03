package io.aos.scala.pmatch;

object MatchSpl1 extends Application {
  
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }

  println(matchTest(3))

}
