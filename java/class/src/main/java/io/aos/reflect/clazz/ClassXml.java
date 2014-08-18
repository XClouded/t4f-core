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
package io.aos.reflect.clazz;

public class ClassXml {

    public static void main(String... argv) throws Exception {
        for (int i = 0; i < argv.length; i++) {
            ClassInfo ci = new ClassInfo(argv[i]);
            System.out.println(formatClass(argv[i], ci, "XXX"));
        }
    }

    // This method is invoked as an XSL extension function from xalan.
    // This should be moved to ClassInfo and this class deleted.
    public static String getClassApi(String pname, String cname, String protFlag) throws Exception {
        boolean prot = false;
        if ((protFlag != null) && (protFlag.equals("true"))) {
            prot = true;
        }
        ClassInfo ci = new ClassInfo(pname + "." + cname, prot);
        return (ci.toString("    ", false));
    }

    private static String formatClass(String classname, ClassInfo ci, String desc) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("<sect2><title>");
        sb.append("<classname>");
        sb.append(classname);
        sb.append("</classname></title>\n");
        sb.append("<para>" + desc + "</para>");
        sb.append("<para><emphasis>");

        if (ci.isInterface()) {
            sb.append("Interface ");
        }
        else {
            sb.append("Class ");
        }

        sb.append("Definition</emphasis></para>");
        sb.append("<blockquote><programlisting>");
        ci.stringify(sb, "    ", 0, true);
        sb.append("</programlisting></blockquote>");
        sb.append("<para><emphasis>See Also: </emphasis>");
        sb.append("</sect2>");

        return (sb.toString());
    
    }

}
