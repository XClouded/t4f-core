/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package io.aos.rhino;

import static io.aos.rhino.script.RhinoScriptTestSupport.scriptPath;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import io.aos.rhino.RhinoShell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NodeVisitor;

public class RhinoScriptTest {

    @Test
    public void testRhinoShell() throws ClassNotFoundException {
        RhinoShell.main(scriptPath("echo.js"), "foo", "bar");
    }

    @Test
    public void testEcho() throws FileNotFoundException, ClassNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByMimeType("text/javascript");
        Bindings bindings = engine.getBindings(ScriptContext.GLOBAL_SCOPE);
        bindings.put("arguments", new String[] { "foo", "bar" });
        engine.put(ScriptEngine.FILENAME, "echo.js");
        Object object = engine.eval(new FileReader(scriptPath("echo.js")));
        assertNotNull(object);
        System.out.println(object);
    }

    @Test
    public void testStates() throws FileNotFoundException, ClassNotFoundException, ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByMimeType("text/javascript");
        engine.put(ScriptEngine.FILENAME, "states1.js");
        Object object = engine.eval(new FileReader(scriptPath("states1.js")));
        assertNotNull(object);
        System.out.println(object);
    }

    @Test
    public void testEvalFunction() throws Exception {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        Bindings bindings = engine.createBindings();
        bindings.put("currentTime", new Date());
        Object o1 = engine.eval("function run(x) { println('x=' + x); }" + "run(currentTime);", bindings);
        assertNull(o1);
        System.out.println(o1);
        Object o2 = engine.eval("function test() {return 'hello';};" + "test();", bindings);
        assertNotNull(o2);
        System.out.println(o2);
        Object o3 = engine.eval(
                "var j = {'AD': { '02': 'Canillo', '07': 'Andorra la Vella', '08': 'Escaldes-Engordany' } };"
                        , bindings);
        assertNotNull(o3);
    }

    @Test
    public void testEvalString() throws ClassNotFoundException, IOException {
        Context context = null;
        try {
            context = Context.enter();
            Scriptable scope = context.initStandardObjects();
            Object o1 = context.evaluateString(scope, "var i = 0;", "<cmd>", 1, null);
            System.out.println(Context.toString(o1));
            Object o2 = context.evaluateString(scope, "function test() {return 'hello';}; test();", "<cmd>", 1, null);
            System.out.println(Context.toString(o2));
            Scriptable j = (Scriptable) context.evaluateString(scope,
                    "function test() {var i = {'AD': { '02': 'Canillo', '07': 'Andorra' } }; return i;};"
                            + "test();", "<cmd>", 1, null);
            assertNotNull(j);
            System.out.println(((Scriptable) j.get("AD", scope)).get("02", scope));
        }
        finally {
            Context.exit();
        }
    }
    
    @Test
    public void testJson() throws FileNotFoundException, ScriptException, ClassNotFoundException {
        
        final ScriptEngineManager factory = new ScriptEngineManager();
        final ScriptEngine engine = factory.getEngineByName("js");
        Object val = null;
        Scanner s = null;
        try {
            s = new Scanner(new File(scriptPath("foo.json")));
            s.useDelimiter("\\Z");
            engine.getBindings(ScriptContext.GLOBAL_SCOPE).put("json", s.next());
        } finally {
            if (s != null) {
                s.close();
            }
        }
        // The default JavaScript engine in Java 6 does not have JSON.parse
        // but eval('(' + json + ')') would work
        val = engine.eval("JSON.parse(json)");

        // The default value is probably a JavaScript internal object and not very useful
        System.out.println(val.getClass() + " = " + val);
        // Java 7 uses Rhino 1.7R3 the objects will implement Map or List where appropriate
        // So in Java 7 we can turn this into something a little more useable
        // This is where Java 6 breaks down, in Java 6 you would have to use the 
        // sun.org.mozilla.javascript.internal classes to get any useful data
        System.out.println(convertJson(val));
    }
    
    @SuppressWarnings("unchecked")
    private Object convertJson(final Object val) {
        if (val instanceof Map) {
            final Map<String, Object> result = new HashMap<String, Object>();
            for (final Map.Entry<String, Object> entry: ((Map<String, Object>) val).entrySet()) {
                result.put(entry.getKey(), convertJson(entry.getValue()));
            }
            return result;
        } else if (val instanceof List) {
            final List<Object> result = new ArrayList<Object>();
            for (final Object item: ((List<Object>) val)) {
                result.add(convertJson(item));
            }
            return result;
        }
        if (val != null) {
            System.out.println(val.getClass() + " = " + val);
        }
        return val;
    }
    
    
    @Test
    public void test1() throws Exception {
        String[] args = new String[]{"var i = 0;"};
        Context cx = Context.enter();
        try {
            
            Scriptable scope = cx.initStandardObjects();

            // Use the Counter class to define a Counter constructor
            // and prototype in JavaScript.
            ScriptableObject.defineClass(scope, Counter.class);

            // Create an instance of Counter and assign it to
            // the top-level variable "myCounter". This is
            // equivalent to the JavaScript code
            // myCounter = new Counter(7);
            Object[] arg = { new Integer(7) };
            Scriptable myCounter = cx.newObject(scope, "Counter", arg);
            scope.put("myCounter", scope, myCounter);

            String s = "";
            for (int i = 0; i < args.length; i++) {
                s += args[i];
            }
            Object result = cx.evaluateString(scope, s, "<cmd>", 1, null);
            System.err.println(Context.toString(result));
        }
        finally {
            Context.exit();
        }
    }

    @Test
    public void testEvalReader() throws ClassNotFoundException, IOException {
        Context context = null;
        try {
            context = Context.enter();
            Scriptable scope = context.initStandardObjects();
            Object result = context.evaluateReader(scope, new FileReader(scriptPath("states1.js")), "<cmd>", 1, null);
            System.out.println(Context.toString(result));
        }
        finally {
            Context.exit();
        }
    }

    @Test
    public void testAst() throws IOException {
        final Set<String> names = new HashSet<String>();
        class Visitor implements NodeVisitor {
            @Override
            public boolean visit(AstNode node) {
                if (node instanceof Name) {
                    names.add(node.getString());
                }
                return true;
            }
        }
        String script = "(V1ND < 0 ? Math.abs(V1ND) : 0)";
        AstNode node = new Parser().parse(script, "<cmd>", 1);
        node.visit(new Visitor());
        System.out.println(names);
    }

}
