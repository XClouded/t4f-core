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
package io.aos.concurrent.count;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxCount {
    public static void main(String... args) 
        throws SAXException,IOException, ParserConfigurationException
    {
        // Create a parser factory and use it to create a parser
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        // This is the name of the file you're parsing
        String filename = args[0];
        // Instantiate a DefaultHandler subclass to do your counting for you
        CountHandler handler = new CountHandler();
        // Start the parser. It reads the file and calls methods of the 
         // handler.
        parser.parse(new File(filename), handler);
        // When you're done, report the results stored by your handler object
        System.out.println(filename + " contains " + handler.numElements +
                           " elements and " + handler.numChars +
                           " other characters ");
    }

    // This inner class extends DefaultHandler to count elements and text in
    // the XML file and saves the results in public fields. There are many
    // other DefaultHandler methods you could override, but you need only 
    // these.
    public static class CountHandler extends DefaultHandler {
        public int numElements = 0, numChars = 0;  // Save counts here
        // This method is invoked when the parser encounters the opening tag
        // of any XML element. Ignore the arguments but count the element.
        public void startElement(String uri, String localname, String qname,
                                Attributes attributes) {
            numElements++;
        }

        // This method is called for any plain text within an element
        // Simply count the number of characters in that text
        public void characters(char[] text, int start, int length) {
            numChars += length;
        }
    }
}
