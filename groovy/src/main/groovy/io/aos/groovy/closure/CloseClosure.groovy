package io.aos.groovy.closure

class Employee {
    def salary
}

def highPaid(emps) {
    def threshold = 150
    return emps.findAll{ e -> e.salary > threshold }
}

def emps = [180, 140, 160].collect{ val -> new Employee(salary:val) }

println emps.size()           // prints 3
println highPaid(emps).size() // prints 2
