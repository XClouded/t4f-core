function doSomething( var0, var1){
 alert( 'var0: ' + var0 + ', var1: ' + var1);
}

doSomething( 'value0', 'value1', 'value2' ); //one argument more than in the defined function

def doSomething = {var0, var1 = null -> }

doSomething( 'value0' )

doSomething( 'value0' , 'value1')

// sample entity
class User{
 def username, password, version, salt = 'RANDOM';
}

// your API, provide a Map of changes to update a entity. the map value may be static value, or a closure that take up to 2 params
def update( entity, Map changes ){
 changes?.each {k, v ->
  def newValue;
  if (v instanceof Closure){
   switch (v.parameterTypes.length) {
    case 0: newValue = v(); break;
    case 1: newValue = v(entity[k]); break; // if one params, the closure is called with the field value
    case 2: newValue = v(entity[k],entity); break; // if two params, the closure is called with teh field value and the entity
   }
  }else{
   newValue = v
  }
  entity[k] = newValue
 }
}

// user code
def user1 = new User(username:'user1', password:'pass1', version:0)
update( user1, [password:{p,e-> Hash.md5(p, e.salt) }, version:{v-> v+1 }]) //assume there is a MD5 util
