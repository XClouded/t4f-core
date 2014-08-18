// an inclusive range
def range = 5..8
assert range.size() == 4
assert range.get(2) == 7
assert range[2] == 7
assert range instanceof java.util.List
assert range.contains(5)
assert range.contains(8)

// lets use an exclusive range
range = 5..<8
assert range.size() == 3
assert range.get(2) == 7
assert range[2] == 7
assert range instanceof java.util.List
assert range.contains(5)
assert ! range.contains(8)

//get the end points of the range without using indexes
def range2 = 1..10
assert range2.from == 1
assert range2.to == 10

for (i in 1..10) {
    println "Hello ${i}"
}

(1..10).each { i ->
    println "Hello ${i}"
}

def years = 5
switch (years) {
   case 1..10: interestRate = 0.076; break;
   case 11..25: interestRate = 0.052; break;
   default: interestRate = 0.037;
}

// an inclusive range
def range3 = 'a'..'d'
assert range3.size() == 4
assert range3.get(2) == 'c'
assert range3[2] == 'c'
assert range3 instanceof java.util.List
assert range3.contains('a')
assert range3.contains('d')
assert ! range3.contains('e')
assert range3.contains('e')
