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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GetSectionTitles {
    public static void main(String... args)
        throws IOException, ParserConfigurationException,
               org.xml.sax.SAXException
    {
        // Create a factory object for creating DOM parsers and configure it
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);  // We want to ignore comments
        factory.setCoalescing(true);        // Convert CDATA to Text nodes
        factory.setNamespaceAware(false);   // No namespaces: this is default
        factory.setValidating(false);       // Don't validate DTD: also default

        // Now use the factory to create a DOM parser, a.k.a. DocumentBuilder
        DocumentBuilder parser = factory.newDocumentBuilder();

        // Parse the file and build a Document tree to represent its content
        Document document = parser.parse(new File(args[0]));

        // Ask the document for a list of all <sect1> elements it contains
        NodeList sections = document.getElementsByTagName("sect1");
        // Loop through those <sect1> elements one at a time
        int numSections = sections.getLength();
        for(int i = 0; i < numSections; i++) {
            Element section = (Element)sections.item(i);  // A <sect1>
            // The first Element child of each <sect1> should be a <title>
            // element, but there may be some whitespace Text nodes first, so 
            // loop through the children until you find the first element 
            // child.
            Node title = section.getFirstChild();
            while(title != null && title.getNodeType() != Node.ELEMENT_NODE) 
                title = title.getNextSibling();
            // Print the text contained in the Text node child of this element
            if (title != null)
                System.out.println(title.getFirstChild().getNodeValue());
        }
    }
}
