// In the Eclipse editor, select some code and type CTRL+SHIFT+X
class sandbox {

  def f1() {
    import java.util.ArrayList
    import scala.collection.JavaConversions._
    val array = new ArrayList[Int]
    array.addAll(List(1, 3, 7))
    array.foreach(println(_))
  }
  f1()
  
  def f2() {
    import io.aos.scala._
    ConcatArgs.main(Array("hello1", "hello2"))
  }
  f2()

}
