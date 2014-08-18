package io.aos.groovy.map

def map = [name:"Gromit", likes:"cheese", id:1234]
assert map.get("name") == "Gromit"
assert map.get("id") == 1234
assert map["name"] == "Gromit"
assert map['id'] == 1234
assert map instanceof java.util.Map

def emptyMap = [:]
assert emptyMap.size() == 0
emptyMap.put("foo", 5)
assert emptyMap.size() == 1
assert emptyMap.get("foo") == 5

def map2 = [name:"Gromit", likes:"cheese", id:1234]
assert map2.name == "Gromit"
assert map2.id == 1234

def emptyMap2 = [:]
assert emptyMap2.size() == 0
emptyMap2.foo = 5
assert emptyMap2.size() == 1
assert emptyMap2.foo == 5
