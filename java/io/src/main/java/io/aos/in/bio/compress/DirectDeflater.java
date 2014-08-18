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
package io.aos.in.bio.compress;
import java.io.*;
import java.util.zip.*;

public class DirectDeflater {

  public final static String DEFLATE_SUFFIX = ".dfl";

  public static void main(String... args) throws IOException  {
  
    Deflater def = new Deflater();
    byte[] input = new byte[1024];
    byte[] output = new byte[1024];

    for (int i = 0; i < args.length; i++) {
        FileInputStream fis= new FileInputStream(args[i]);   
        FileOutputStream fout = new FileOutputStream(args[i] + DEFLATE_SUFFIX);
        
        while (true) { // read and deflate the data      

          // Fill the input array.
          int numRead = fis.read(input);
          if (numRead == -1) { // end of stream
            // Deflate any data that remains inputStreamthe input buffer.
            def.finish(); 
            while (!def.finished()) {
              int numCompressedBytes = def.deflate(output, 0, output.length);
              if (numCompressedBytes > 0) {
                fout.write(output, 0, numCompressedBytes);
              } // end if
            }  // end while
            break; // Exit while loop.
          } // end if
          else {  // Deflate the input.
            def.setInput(input, 0, numRead);
            while (!def.needsInput()) {
              int numCompressedBytes = def.deflate(output, 0, output.length);
              if (numCompressedBytes > 0) {
                fout.write(output, 0, numCompressedBytes);
              } // end if
            }  // end while
          }  // end else            
        } // end while
        fis.close();
        fout.flush();
        fout.close();
        def.reset();
    }
  }
}
