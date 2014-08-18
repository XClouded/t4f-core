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
package io.aos.classpath;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class JarWhich {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String USAGE =
      LINE_SEPARATOR +
      "JWhich: find classes or other resources on the Java CLASSPATH." + 
      LINE_SEPARATOR +
      "Usage:" + 
      LINE_SEPARATOR + 
      LINE_SEPARATOR +
      "  java com.fullspan.jwhich.JWhich classOrResourceName1 classOrResourceName2 . . ." +
      LINE_SEPARATOR + 
      LINE_SEPARATOR +
      "For example:" + 
      LINE_SEPARATOR + 
      LINE_SEPARATOR +
      "  java aos.classpath.JarWhich javax/servlet/http/HttpServletRequest.class java/lang/String.class" + 
      LINE_SEPARATOR +
      "  javax/servlet/http/HttpServletRequest.class: jar:file:/home/mitch/prod/jakarta-tomcat-4.1.30/common/lib/servlet.jar!/javax/servlet/http/HttpServletRequest.class" + 
      LINE_SEPARATOR +
      "  java/lang/String.class: jar:file:/usr/java/jdk1.5.0_06/jre/lib/rt.jar!/java/lang/String.class";

    public static void main(String... args) throws Exception {
        if (args.length < 1) {
            System.out.println(USAGE);
            return;
        }
        for (int i = 0; i < args.length; i++) {
            printResourceLocations(args[i]);
        }
    }

    public static void printResourceLocations(String resourceName) throws IOException {
        ClassLoader cld = JarWhich.class.getClassLoader();
        Enumeration<URL> en = cld.getResources(resourceName);
        StringBuffer buf = new StringBuffer();
        buf.append(resourceName);
        buf.append(": ");
        if (en == null || (!en.hasMoreElements())) {
            buf.append("not found");
        } 
        else {
            boolean firstLoc = true;
            while (en.hasMoreElements()) {
                if (!firstLoc) {
                    buf.append(", ");
                }
                URL url = en.nextElement();
                buf.append(url.toString());
                firstLoc = false;
            }
        }
        System.out.println(buf.toString());
    }

}
