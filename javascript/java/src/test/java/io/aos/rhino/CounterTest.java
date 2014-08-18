package io.aos.rhino;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * An example illustrating how to create a JavaScript object and retrieve
 * properties and call methods.
 * <p>
 * Output should be:
 * 
 * <pre>
 * count = 0
 * count = 1
 * resetCount
 * count = 0
 * </pre>
 */
public class CounterTest {

    @Test
    public void testCounter() throws Exception {

        Context context = Context.enter();
        try {
            Scriptable scope = context.initStandardObjects();
            ScriptableObject.defineClass(scope, Counter.class);

            Scriptable testCounter = context.newObject(scope, "Counter");

            Object count = ScriptableObject.getProperty(testCounter, "count");
            System.out.println("count = " + count);

            count = ScriptableObject.getProperty(testCounter, "count");
            System.out.println("count = " + count);

            ScriptableObject.callMethod(testCounter, "resetCount", new Object[0]);
            System.out.println("resetCount");

            count = ScriptableObject.getProperty(testCounter, "count");
            System.out.println("count = " + count);
        }
        finally {
            Context.exit();
        }
    }

}
