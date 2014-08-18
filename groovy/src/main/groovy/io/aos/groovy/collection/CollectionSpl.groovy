assert [1, 3, 5] == ['a', 'few', 'words']*.size()


def words = ['ant', 'buffalo', 'cat', 'dinosaur']
assert words.findAll{ w -> w.size() > 4 } == ['buffalo', 'dinosaur']

words = ['ant', 'buffalo', 'cat', 'dinosaur']
assert words.collect{ it[0] } == ['a', 'b', 'c', 'd']

words = ['ant', 'buffalo', 'cat', 'dinosaur']
assert words.collect{ it[0] } == ['a', 'b', 'c', 'd']

def list = 100..200
def sub = list[1, 3, 20..25, 33]
assert sub == [101, 103, 120, 121, 122, 123, 124, 125, 133]

list = ["a", "b", "c"]
list[2] = "d"
list[0] = list[1]
list[3] = 5
assert list == ["b", "b", "d", 5]

def text = "nice cheese gromit!"
def x = text[-1]
assert x == "!"

def name = text[-7..-2]
assert name == "gromit" 

text = "nice cheese gromit!"
name = text[3..1]
assert name == "eci"
