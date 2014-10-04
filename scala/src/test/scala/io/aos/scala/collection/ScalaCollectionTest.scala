package io.aos.scala.collection

import org.junit.Test
import scala.collection.mutable.ListBuffer

@Test
class ScalaCollection {

  @Test
  def test1() {

    val a = List((1, 2), (3, 4), (3, 6))
    println(a)
    println(a.take(1))

    val b = a.map(tup => tup._1 + tup._2)
    println(b)

    val c = a.par.map(tup => tup._1 + tup._2).toList
    println(c)

  }

  @Test
  def test2() {

    List(1, 2, 3, 4, 5) foreach { i => println("Int: " + i) }

    val stateCapitals = Map(
      "Alabama" -> "Montgomery",
      "Alaska" -> "Juneau",
      "Wyoming" -> "Cheyenne")

    stateCapitals foreach { kv => println(kv._1 + ": " + kv._2) }

  }

  // THESE are all the same as: 0 to 3 toList.
  def listTestA() = {
    var list: List[Int] = Nil
    for (i <- 0 to 3)
      list = list ::: List(i)
    list
  }

  def listTestB() = {
    val list = new ListBuffer[Int]()
    for (i <- 0 to 3)
      list += i
    list.toList
  }

  def listTestC() = {
    def _add(l: List[Int], i: Int): List[Int] = i match {
      case 3 => l ::: List(3)
      case _ => _add(l ::: List(i), i + 1)
    }
    _add(Nil, 0)
  }

}
