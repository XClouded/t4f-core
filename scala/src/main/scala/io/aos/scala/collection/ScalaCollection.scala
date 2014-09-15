package io.aos.scala.collection

object ScalaCollection {

  def main(args: Array[String]) {
      val a = List((1,2), (3,4), (3,6))
      println(a)
      val b = a.map(tup => tup._1 + tup._2)
      println(b)
      val c = a.par.map(tup => tup._1 + tup._2).toList
      println(c)
  }
  
}
