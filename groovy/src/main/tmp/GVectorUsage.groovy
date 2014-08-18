package io.aos.groovy.closure

def gVect = new GVector()
gVect.add(2)
gVect.add(3)
gVect.add(4)

def c = { numberToSquare -> numberToSquare * numberToSquare }

gVect.apply(c) // the elements in the GVector have all been squared.

gVect = new GVector()
gVect.add(2)
gVect.add(3)
gVect.add(4)

def c2 = { value -> println(value) }

gVect.apply(c2) // the elements in the GVector have all been printed.

gVect = new GVector()
gVect.add(2)
gVect.add(3)
gVect.add(4)

gVect.apply{ value -> println(value) }  // elements in GVector have been printed.
