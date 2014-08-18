package io.aos.groovy.closure

class GVector implements java.util.List {
    
  public void apply( c ){
     for (i in 0..<size()){
        this[i] = c(this[i])
     }
  
  }
  
}
