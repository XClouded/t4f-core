package io.aos.groovy.expando

// See http://groovy.codehaus.org/Collections

def player = new Expando()
player.name = "Dierk"
player.greeting = { "Hello, my name is $name" }

println player.greeting()
player.name = "Jochen"
println player.greeting()
