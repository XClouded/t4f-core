package io.aos.guice.simple;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SimpleTest {
    
    @Test
    public void test() {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        
        String s1 = injector.getInstance(String.class);
        assertNotNull(s1);
        
        String s2 = injector.getInstance(String.class);
        assertNotNull(s2);
        
        assertTrue(s1 != s2);
        
    }

}
