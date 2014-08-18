package io.aos.groovy.hello

println "Hello, World!"

println 123+45*67

x = 1
println x

x = new java.util.Date()
println x

x = -3.1499392
println x

x = false
println x

x = "Hi"
println x

myList = [1776, -1, 33, 99, 0, 928734928763]

println myList[0]
println myList.size()

scores = [ "Brett":100, "Pete":"Did not finish", "Andrew":86.87934 ]
println scores["Pete"]
println scores.Pete

scores["Pete"] = 3
println scores["Pete"]


emptyMap = [:]
emptyList = []

println emptyMap.size()
println emptyList.size()

amPM = Calendar.getInstance().get(Calendar.AM_PM)
if (amPM == Calendar.AM) {
    println("Good morning")
} else {
    println("Good evening")
}

amPM = Calendar.getInstance().get(Calendar.AM_PM)
if (amPM == Calendar.AM) {
    println("Have another cup of coffee.")
}

myBooleanVariable = true

titanicBoxOffice = 1234600000
titanicDirector = "James Cameron"

trueLiesBoxOffice = 219000000
trueLiesDirector = "James Cameron"

returnOfTheKingBoxOffice = 752200000
returnOfTheKingDirector = "Peter Jackson"

theTwoTowersBoxOffice = 581200000
theTwoTowersDirector = "PeterJackson"

titanicBoxOffice > returnOfTheKingBoxOffice  // evaluates to true
titanicBoxOffice >= returnOfTheKingBoxOffice // evaluates to true
titanicBoxOffice >= titanicBoxOffice         // evaluates to true
titanicBoxOffice > titanicBoxOffice          // evaluates to false
titanicBoxOffice + trueLiesBoxOffice < returnOfTheKingBoxOffice + theTwoTowersBoxOffice  // evaluates to false

titanicDirector > returnOfTheKingDirector    // evaluates to false, because "J" is before "P"
titanicDirector < returnOfTheKingDirector    // evaluates to true
titanicDirector >= "James Cameron"           // evaluates to true
titanicDirector == "James Cameron"           // evaluates to true

if (titanicBoxOffice + trueLiesBoxOffice > returnOfTheKingBoxOffice + theTwoTowersBoxOffice) {
    println(titanicDirector + " is a better director than " + returnOfTheKingDirector)
}

suvMap = ["Acura MDX":"\$36,700", "Ford Explorer":"\$26,845"]
if (suvMap["Hummer H3"] != null) {
    println("A Hummer H3 will set you back "+suvMap["Hummer H3"]);
}
