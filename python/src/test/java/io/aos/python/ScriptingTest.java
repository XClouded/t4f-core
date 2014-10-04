package io.aos.python;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.junit.Test;

public class ScriptingTest {

    @Test
    public void test() throws FileNotFoundException, ScriptException {
        
        StringWriter writer = new StringWriter(); //ouput will be stored here

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();

        for (ScriptEngineFactory factory :
            manager.getEngineFactories()) {
            System.out.println("Available language: " +
                factory.getLanguageName());
        }
        
        context.setWriter(writer); //configures output redirection
        ScriptEngine engine = manager.getEngineByName("python");
        engine.eval(new FileReader("src/main/python/numbers.py"), context);
        System.out.println(writer.toString()); 

    }

}
