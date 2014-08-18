package io.aos.reflect.clazz;

import io.aos.reflect.clazz.ClassXml;

import org.junit.Test;

public class ClassXmlTest {
    
    @Test
    public void test() throws Exception {
        ClassXml.main(new String[]{String.class.getName()});
    }

}
