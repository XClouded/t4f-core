package io.aos.python;

import org.junit.Test;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class JythonTest {

    @Test
    public void test() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("import sys\n" + "sys.path.append('pathToModiles if they're not there by default')\n"
                + "import yourModule");
        // execute a function that takes a string and returns a string
        PyObject someFunc = interpreter.get("funcName");
        PyObject result = someFunc.__call__(new PyString("Test!"));
        String realResult = (String) result.__tojava__(String.class);
        System.out.println(realResult);
    }

    public void test2() {

        PythonInterpreter python = new PythonInterpreter();

        int number1 = 10;
        int number2 = 32;

        python.set("number1", new PyInteger(number1));
        python.set("number2", new PyInteger(number2));
        python.exec("number3 = number1+number2");
        PyObject number3 = python.get("number3");
        System.out.println("val : " + number3.toString());
    }

    public void test3() {

        PythonInterpreter.initialize(System.getProperties(), System.getProperties(), new String[0]);
        PythonInterpreter interp = new PythonInterpreter();
        interp.execfile("foo.py");
    }

}
