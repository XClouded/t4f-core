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
package io.aos.serde;

import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Ignore;
import org.junit.Test;

public class SerializableImplementsTest {

    @Test
    @Ignore
    public void test() {

        String[] prefixes = { "java.applet", "java.awt", "java.awt.datatransfer", "java.awt.event", "java.awt.image",
                "java.beans", "java.io", "java.lang", "java.lang.reflect", "java.math", "java.net", "java.rmi",
                "java.rmi.dgc", "java.rmi.registry", "java.rmi.server", "java.security", "java.security.acl",
                "java.security.interfaces", "java.sql", "java.text", "java.util", "java.util.zip" };
        String classname = "";
        FileDialog fd = new FileDialog(new Frame(), "Please locate classes.zip: ", FileDialog.LOAD);
        fd.setVisible(true);
        String filename = fd.getDirectory() + fd.getFile();

        /*
         * try { prefix = args[0]; if (!prefix.endsWith(".")) prefix += "."; }
         * catch (ArrayIndexOutOfBoundsException e) {
         * }
         */

        for (int i = 0; i < prefixes.length; i++) {
            String prefix = prefixes[i] + ".";
            System.out.println(prefix + ":\n");
            try {
                ZipFile zf = new ZipFile(filename);
                Enumeration files = zf.entries();
                while (files.hasMoreElements()) {
                    ZipEntry ze = (ZipEntry) files.nextElement();
                    classname = ze.getName().replace('/', '.');
                    if (classname.startsWith(prefix) && classname.endsWith(".class")
                            && !classname.startsWith("sun.tools.javadoc.MIFPrintStream")
                            && !classname.startsWith("sun.awt.motif.X11Selection.class")
                            && !classname.startsWith("sun.awt.tiny.TinyWindowFrame")
                            && !classname.startsWith("sun.awt.tiny.TinyMenuBarPeer")) {
                        // System.err.println(classname);
                        classname = classname.substring(0, classname.length() - 6);
                        try {
                            Class thisClass = Class.forName(classname);
                            classname = classname.substring(prefix.length());
                            if (classname.indexOf('.') != -1 || classname.indexOf('$') != -1)
                                continue;
                            if (implementsSerializable(thisClass)) {
                                System.out.print(classname + ", ");
                            }
                        } catch (InternalError ie) {
                            System.err.println(ie);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println(e + " " + classname);
            } catch (Exception e) {
                System.err.println(e);
            } catch (Throwable t) {
                System.err.println(t);
            }
            System.out.println();
        }

    }

    public static boolean implementsSerializable(Class c) {
        Class[] theInterfaces = c.getInterfaces();
        for (int i = 0; i < theInterfaces.length; i++) {
            if (theInterfaces[i].getName().endsWith("Serializable")) {
                return true;
            }
        }
        return false;
    }

}
