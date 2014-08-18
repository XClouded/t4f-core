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
package io.aos.reflect.annotation;

import io.aos.reflect.annotation.MyAnnotation;
import io.aos.reflect.annotation.What;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

@What(description = "An annotation test class", clazz = AnnotationTest.class)
@MyAnnotation(stringValue = "for class", intValue = 100)
public class AnnotationTest {

    @What(description = "An annotation test field", clazz = AnnotationTest.class)
    @MyAnnotation(stringValue = "for field", intValue = 100)
    public String field;
    
    @What(description = "An annotation test method", clazz = AnnotationTest.class)
    @MyAnnotation(stringValue = "Annotation Example", intValue = 100)
    public void myMethod(@What(description = "An annotation test method", clazz = AnnotationTest.class) String str, int i) {
    }

    @Test
    public void testClassAnnotations() {
        printAnnotations("All class annotations", this.getClass().getAnnotations());
        printAnnotations("The What class annotations", this.getClass().getAnnotation(What.class));
    }   
    
    @Test
    public void testMethodAnnotations() {
        Method[] methods = this.getClass().getMethods();
        for (Method method:  methods) {
            printAnnotations("Annotations for method " + method.getName(), method.getAnnotations());
        }
    }
    
    @Test
    public void testParameterMethodAnnotations() {
        Method[] methods = this.getClass().getMethods();
        for (Method method:  methods) {
 //           printAnnotations("Annotations for parameter method " + method.getName(), method.getParameterAnnotations());
        }
    }
    
    @Test
    public void testFieldAnnotations() {
        Field[] fields = this.getClass().getFields();
        for (Field field:  fields) {
            printAnnotations("Annotations for field " + field.getName(), field.getAnnotations());
        }
    }
    
    private void printAnnotations(String message, Annotation... annotations) {
        System.out.println(message);
        for (Annotation a : annotations) {
            System.out.println("  " + a.getClass().getName());
        }
    }

}
