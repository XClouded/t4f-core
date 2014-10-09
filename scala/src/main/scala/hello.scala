// Call with "scala hello.scala 'This is me'"
object HelloWorld {

  def main(args: Array[String]) {
    println("Hello, world!")
    println(args(0))
  }

}
