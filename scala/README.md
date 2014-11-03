-------------------------------------------------------------------------------
```
 _____ ___ _____    _____         _     
|_   _| | |   __|  |   __|___ ___| |___ 
  | | |_  |   __|  |__   |  _| .'| | .'|
  |_|   |_|__|     |_____|___|__,|_|__,|
                                        
 #t4f-scala
```
-------------------------------------------------------------------------------

# How to run Scala code from Eclipse

* Run as Application (Right-click and select Run As > Scala Application) - You need a main method (or your class should extends App)
* Open the Scala Interpreter window (via menu Window, Show View), select code in Eclipse editor and type CTRL+SHIFT+X (see e.g. sandbox.scala)
* Create a Scala Worksheet (read more https://github.com/scala-ide/scala-worksheet/wiki/Getting-Started - You must install the plugin separately)
* Run io.aos.scala.repl.AosRepl

-------------------------------------------------------------------------------

# Notes

A for loop is a repetition control structure that allows you to efficiently write a loop that needs to execute a specific number of times. There are various forms of for loop in Scala which are described below:
The for Loop with Ranges

The simplest syntax of a for loop in Scala is:

for( var x <- Range ){
   statement(s);
}

Here, the Range could be a range of numbers and that is represented as i to j or sometime like i until j. The left-arrow <- operator is called a generator, so named because it's generating individual values from a range.
Example:

Following is the example of for loop with range using i to j syntax:

object Test {
   def main(args: Array[String]) {
      var a = 0;
      // for loop execution with a range
      for( a <- 1 to 10){
         println( "Value of a: " + a );
      }
   }
}

object Test {
   def t() {
      var a = 0;
      // for loop execution with a range
      for( a <- 1 to 10){
         println( "Value of a: " + a );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
value of a: 1
value of a: 2
value of a: 3
value of a: 4
value of a: 5
value of a: 6
value of a: 7
value of a: 8
value of a: 9
value of a: 10

C:/>

Following is the example of for loop with range using i until j syntax:

object Test {
   def main(args: Array[String]) {
      var a = 0;
      // for loop execution with a range
      for( a <- 1 until 10){
         println( "Value of a: " + a );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
value of a: 1
value of a: 2
value of a: 3
value of a: 4
value of a: 5
value of a: 6
value of a: 7
value of a: 8
value of a: 9

C:/>

You can use multiple ranges separated by semicolon (;) within a for loop and in that case loop will iterate through all the possible computations of the given ranges. Following is an example of using just two ranges, you can use more than two ranges as well.

object Test {
   def main(args: Array[String]) {
      var a = 0;
      var b = 0;
      // for loop execution with a range
      for( a <- 1 to 3; b <- 1 to 3){
         println( "Value of a: " + a );
         println( "Value of b: " + b );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
Value of a: 1
Value of b: 1
Value of a: 1
Value of b: 2
Value of a: 1
Value of b: 3
Value of a: 2
Value of b: 1
Value of a: 2
Value of b: 2
Value of a: 2
Value of b: 3
Value of a: 3
Value of b: 1
Value of a: 3
Value of b: 2
Value of a: 3
Value of b: 3

C:/>

The for Loop with Collections

The syntax of a for loop with collection is as follows:

for( var x <- List ){
   statement(s);
}

Here, the List variable is a collection type having a list of elements and for loop iterate through all the elements returning one element in x variable at a time.
Example:

Following is the example of for loop with a collection of numbers. Here we created this collection using List(). We will study collections in a separate chapter.

object Test {
   def main(args: Array[String]) {
      var a = 0;
      val numList = List(1,2,3,4,5,6);

      // for loop execution with a collection
      for( a <- numList ){
         println( "Value of a: " + a );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
value of a: 1
value of a: 2
value of a: 3
value of a: 4
value of a: 5
value of a: 6

C:/>

The for Loop with Filters

Scala's for loop allows to filter out some elements using one or more if statement(s). Following is the syntax of for loop along with filters.

for( var x <- List
      if condition1; if condition2...
   ){
   statement(s);
}

To add more than one filter to a for expression, separate the filters with semicolons(;).
Example:

Following is the example of for loop along with filters:

object Test {
   def main(args: Array[String]) {
      var a = 0;
      val numList = List(1,2,3,4,5,6,7,8,9,10);

      // for loop execution with multiple filters
      for( a <- numList
           if a != 3; if a < 8 ){
         println( "Value of a: " + a );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
value of a: 1
value of a: 2
value of a: 4
value of a: 5
value of a: 6
value of a: 7

C:/>

The for Loop with yield:

You can store return values from a for loop in a variable or can return through a function. To do so, you prefix the body of the for expression by the keyword yield as follows:

var retVal = for{ var x <- List
     if condition1; if condition2...
}yield x

Note the curly braces have been used to keep the variables and conditions and retVal is a variable where all the values of x will be stored in the form of collection.
Example:

Following is the example to show the usage of for loop along with yield:

object Test {
   def main(args: Array[String]) {
      var a = 0;
      val numList = List(1,2,3,4,5,6,7,8,9,10);

      // for loop execution with a yield
      var retVal = for{ a <- numList 
                        if a != 3; if a < 8
                      }yield a

      // Now print returned values using another loop.
      for( a <- retVal){
         println( "Value of a: " + a );
      }
   }
}

When the above code is compiled and executed, it produces the following result:

C:/>scalac Test.scala
C:/>scala Test
value of a: 1
value of a: 2
value of a: 4
value of a: 5
value of a: 6
value of a: 7

C:/>


-------------------------------------------------------------------------------
execCommand \
  "${JAVACMD:=java}" \
  $JAVA_OPTS \
  "${java_args[@]}" \
  $(classpathArgs) \
  -Dscala.home="$SCALA_HOME" \
  -Dscala.usejavacp=true \
  $EMACS_OPT \
  $WINDOWS_OPT \
   scala.tools.nsc.MainGenericRunner  "$@"
-------------------------------------------------------------------------------
java -cp $SCALA_HOME/lib/scala-*.jar scala.tools.nsc.MainGenericRunner Class.scala
-------------------------------------------------------------------------------
$ scala
scala> println("Scala is installed!")
Scala is installed!
scala> :quit
-------------------------------------------------------------------------------
$ scala
scala> val a = 1
a: Int = 1
scala> var b = 2
b: Int = 2
scala> b = b + a
b: Int = 3
scala> a = 2
<console>6: error: reassignment to val
       a = 2
         ^
-------------------------------------------------------------------------------
scala> def square(x: Int) = x*x
square: (x: Int)Int
scala> square(3)
res0: Int = 9
scala> square(res0)
res1: Int = 81
-------------------------------------------------------------------------------
scala> class Dog( name: String ) {
     |   def bark() = println(name + " barked")
     | }
defined class Dog
scala> val stubby = new Dog("Stubby")
stubby: Dog = Dog@1dd5a3d
scala> stubby.bark
Stubby barked
scala>
-------------------------------------------------------------------------------
$ vi HelloWorld.scala
object HelloWorld {
  def main(args: Array[String]) {
    println("Hello, world!")
  }
}
---
$ scalac HelloWorld.scala
$ scala -cp . HelloWorld
-------------------------------------------------------------------------------
$ bin/console
Welcome to Scala 2.10.2 (OpenJDK 64-Bit Server VM, Java 1.6.0_27)

In [1]: 1
Out[1]: 1

In [2]: 1 + 2 + 3
Out[2]: 6

In [3]: (1 to 5).foreach { i => println(i); Thread.sleep(1000) }
1
2
3
4
5

In [4]: val x = 1
Out[4]: 1

In [5]: x
Out[5]: 1

In [6]: 100*x + 17
Out[6]: 117

In [7]: x.<TAB>
x.%             x.-             x.>>            x.isInstanceOf  x.toFloat       x.toString      x.|
x.&             x./             x.>>>           x.toByte        x.toInt         x.unary_+
x.*             x.>             x.^             x.toChar        x.toLong        x.unary_-
x.+             x.>=            x.asInstanceOf  x.toDouble      x.toShort       x.unary_~

In [7]: x.to<TAB>
x.toByte    x.toChar    x.toDouble  x.toFloat   x.toInt     x.toLong    x.toShort   x.toString

In [7]: x.toS<TAB>
x.toShort   x.toString

In [7]: 1/0
java.lang.ArithmeticException: / by zero

In [8]: java.util.UUID.fromString("abc")
java.lang.IllegalArgumentException: Invalid UUID string: abc
    java.util.UUID.fromString(UUID.java:226)

In [9]: class Foo(a: Int) { def bar(b: String) = b*a }

In [10]: new Foo(5)
Out[10]: Foo@70f4d063

In [11]: _10.bar("xyz")
Out[11]: xyzxyzxyzxyzxyz

In [12]: import scala.language.experimental.macros

In [13]: import scala.reflect.macros.Context

In [14]: object Macros {
    ...:     def membersImpl[A: c.WeakTypeTag](c: Context): c.Expr[List[String]] = {
    ...:         import c.universe._
    ...:         val tpe = weakTypeOf[A]
    ...:         val members = tpe.declarations.map(_.name.decoded).toList.distinct
    ...:         val literals = members.map(member => Literal(Constant(member)))
    ...:         c.Expr[List[String]](Apply(reify(List).tree, literals))
    ...:     }
    ...:
    ...:     def members[A] = macro membersImpl[A]
    ...: }
    ...:

In [15]: Macros.members[Int]
Out[15]: List(<init>, toByte, toShort, toChar, toInt, toLong, toFloat, toDouble, unary_~,
unary_+, unary_-, +, <<, >>>, >>, ==, !=, <, <=, >, >=, |, &, ^, -, *, /, %, getClass)

-------------------------------------------------------------------------------

