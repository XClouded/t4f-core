package io.aos.groovy.expandometaclass
// See http://groovy.codehaus.org/ExpandoMetaClass

String.metaClass.swapCase = {->
      def sb = new StringBuffer()
      delegate.each {
           sb << (Character.isUpperCase(it as char) ? Character.toLowerCase(it as char) : 
                   Character.toUpperCase(it as char))
      }
      sb.toString()
}

def s = "test"

println s

sw = s.swapCase()

println sw
