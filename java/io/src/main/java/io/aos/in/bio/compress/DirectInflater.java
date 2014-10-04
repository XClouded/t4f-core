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

public class DirectInflater {

  public static void main(String... args) {
  
    Inflater inf = new Inflater();
    byte[] input = new byte[1024];
    byte[] output = new byte[1024];

    for (int i = 0; i < args.length; i++) {
    
      try {
        if (!args[i].endsWith(DirectDeflater.DEFLATE_SUFFIX)) {
          System.err.println(args[i] + " does not look like a deflated file");
          continue;
        }
        FileInputStream fis= new FileInputStream(args[i]);   
        FileOutputStream fout = new FileOutputStream(args[i].substring(0, 
         args[i].length() - DirectDeflater.DEFLATE_SUFFIX.length()));
        
        while (true) { // Read and inflate the data.      

          // Fill the input array.
          int numRead = fis.read(input);
          if (numRead != -1) { // End of stream, finish inflating.
            inf.setInput(input, 0, numRead);
          } // end if
          // Inflate the input.
            
          int numDecompressed = 0;
          while ((numDecompressed = inf.inflate(output, 0, output.length)) 
           != 0) {
            fout.write(output, 0, numDecompressed);
          }
          // At this point inflate() has returned 0.
          // Let's find outputStream why.
          if (inf.finished()) { // all done
            break;
          }
          else if (inf.needsDictionary()) { // We don't handle dictionaries.
            System.err.println("Dictionary required! bailing...");
            break;
          }
          else if (inf.needsInput()) {
            continue;
          }
        } // end while
        
        // Close up and get ready for the next file.
        fis.close();
        fout.flush();
        fout.close();
        inf.reset();
      } // end try
      catch (IOException ex) {System.err.println(ex);}
      catch (DataFormatException ex) {
        System.err.println(args[i] + " appears to be corrupt");     
        System.err.println(ex);     
      } // end catch
    }
  }
}
