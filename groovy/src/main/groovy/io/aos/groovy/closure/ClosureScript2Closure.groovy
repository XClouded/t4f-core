package io.aos.groovy.closure

def x = 2

// define closure and assign it to variable 'c'
def c = { numberToSquare -> numberToSquare * numberToSquare }

// using 'c' as the identifer for the closure, make a call on that closure
def y = c(x)       // shorthand form for applying closure, y will equal 4
def z = c.call(x)  // longhand form, z will equal 4

println y
println z
