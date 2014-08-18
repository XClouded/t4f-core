package io.aos.groovy.closure

def clos = { println "hello!" }

println "Executing the Closure:"
clos()                          //prints "hello!"

def printSum = { a, b -> print a+b }
printSum( 5, 7 )                       //prints "12"

def myConst = 5
def incByConst = { num -> num + myConst }
println incByConst(10) // => 15


def localMethod() {
    def localVariable = new java.util.Date()
    return { println localVariable }
}
  
def clos1 = localMethod()
  
println "Executing the Closure:"
clos1()                          //prints the date when "localVariable" was defined

def clos3 = { print it }
clos3( "hi there" )              //prints "hi there"

class Class1 {
    def closure = {
      println this.class.name
      println delegate.class.name
      def nestedClos = {
        println owner.class.name
      }
      nestedClos()
    }
  }
  
def clos4 = new Class1().closure
clos4.delegate = this
clos4()
  
def list = ['a','b','c','d']
def newList = []

list.collect( newList ) {
  it.toUpperCase()
}
println newList           //  ["A", "B", "C", "D"]

def list2 = ['a','b','c','d']
def newList2 = []

def clos5 = { it.toUpperCase() }
list.collect( newList2, clos5 )

assert newList == ["A", "B", "C", "D"]
