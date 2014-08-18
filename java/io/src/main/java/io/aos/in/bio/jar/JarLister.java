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
package io.aos.in.bio.jar;
import java.util.*;
import java.util.zip.*;
import java.util.jar.*;
import java.io.*;

public class JarLister {

  public static void main(String... args) throws IOException {

    JarFile jf = new JarFile(args[0]);
    Enumeration e = jf.entries();
    while (e.hasMoreElements()) {
      JarEntry je = (JarEntry) e.nextElement();
      String name = je.getName();
      Date lastModified = new Date(je.getTime());
      long uncompressedSize = je.getSize();
      long compressedSize = je.getCompressedSize();
      long crc = je.getCrc();
      int method = je.getMethod();
      String comment = je.getComment();
      
      if (method == ZipEntry.STORED) {
        System.out.println(name + " was stored at " + lastModified);          
        System.out.println("with a size of  " + uncompressedSize 
         + " bytes"); 
      }
      else if (method == ZipEntry.DEFLATED) {
        System.out.println(name + " was deflated at " + lastModified);
        System.out.println("from  " + uncompressedSize + " bytes to " 
         + compressedSize + " bytes, a savings of " 
         + (100.0 - 100.0*compressedSize/uncompressedSize) + "%");
      }
      else {
        System.out.println(name 
         + " was compressed using an unrecognized method at " 
         + lastModified);
        System.out.println("from  " + uncompressedSize + " bytes to " 
         + compressedSize + " bytes, a savings of " 
         + (100.0 - 100.0*compressedSize/uncompressedSize) + "%");
      }
      System.out.println("Its CRC is " + crc);
      if (comment != null && !comment.equals("")) {
        System.out.println(comment);
      }
      if (je.isDirectory()) {
        System.out.println(name + " is a directory");
      }
      Attributes a = je.getAttributes();
      if (a != null) {
        Object[] nameValuePairs = a.entrySet().toArray();
        for (int j = 0; j < nameValuePairs.length; j++) {
          System.out.println(nameValuePairs[j]);
        }
      }
      System.out.println();
    }
  }
}
