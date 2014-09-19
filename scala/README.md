-------------------------------------------------------------------------------
```
 _____ ___ _____    _____         _     
|_   _| | |   __|  |   __|___ ___| |___ 
  | | |_  |   __|  |__   |  _| .'| | .'|
  |_|   |_|__|     |_____|___|__,|_|__,|
                                        
 #t4f-scala
```
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