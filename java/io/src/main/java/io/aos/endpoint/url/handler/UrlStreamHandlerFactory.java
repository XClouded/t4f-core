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
package io.aos.endpoint.url.handler;

import io.aos.endpoint.url.handler.chargen.ChargenHandler;
import io.aos.endpoint.url.handler.daytime.DayTimeHandler;
import io.aos.endpoint.url.handler.finger.FingerHandler;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class UrlStreamHandlerFactory implements URLStreamHandlerFactory {

   public URLStreamHandler createURLStreamHandler(String protocol) {
    if (protocol.equalsIgnoreCase("chargen")) {
      return new ChargenHandler();
    }
    else if (protocol.equalsIgnoreCase("daytime")) {
        return new DayTimeHandler();
      }
    else if (protocol.equalsIgnoreCase("finger")) {
      return new FingerHandler();
    }
    throw new IllegalArgumentException();
  }

}
