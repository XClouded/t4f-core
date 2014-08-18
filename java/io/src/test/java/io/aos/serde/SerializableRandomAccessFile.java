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
import java.io.*;

public class SerializableRandomAccessFile extends RandomAccessFile 
 implements Serializable {

  public SerializableRandomAccessFile(String name, String mode) 
   throws IOException {
    super(name, mode);
  }
  
  public SerializableRandomAccessFile(File file, String mode) 
   throws IOException {
    super(file, mode);
  }

  public static void main(String... args) {

    try {
      SerializableRandomAccessFile sraf = new 
       SerializableRandomAccessFile(args[0], args[1]);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(sraf);
      oos.close();
      System.out.println("Wrote object!");

      ByteArrayInputStream bis = new 
       ByteArrayInputStream(bos.toByteArray());
      ObjectInputStream ois = new ObjectInputStream(bis);
      Object o = ois.readObject();
      System.out.println("Read object!");
    }
    catch (Exception e) {
      System.err.println(e);
    }

  }

}
