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
package io.aos.endpoint.url;
import java.net.*;

public class UrlDecoderTest {

  public static void main(String... args) {
  
    String input = "http://www.altavista.com/cgi-bin/query?pg=q&kl=XX&stype=stext&q=%2B%22Java+Network+Programming%22&search.x=30&search.y=7";
    System.out.println(input);
    try {
      String output = URLDecoder.decode(input);
      System.out.println(output);
    }
    catch (Exception e) {
      System.err.println("Malformed URL");
    }
    
  }



}
