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
package io.aos.concurrent.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomToStream {

    public static void main(String... args) throws ParserConfigurationException, TransformerConfigurationException,
            TransformerException {

        // Create a DocumentBuilderFactory and a DocumentBuilder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Instead of parsing an XML document, however, just create an empty
        // document that you can build up yourself.
        Document document = db.newDocument();

        // Now build a document tree using DOM methods
        Element book = document.createElement("book"); // Create new element
        book.setAttribute("id", "javanut4"); // Give it an attribute
        document.appendChild(book); // Add to the document
        for (int i = 1; i <= 3; i++) { // Add more elements
            Element chapter = document.createElement("chapter");
            Element title = document.createElement("title");
            title.appendChild(document.createTextNode("Chapter " + i));
            chapter.appendChild(title);
            chapter.appendChild(document.createElement("para"));
            book.appendChild(chapter);
        }

        // Now create a TransformerFactory and use it to create a Transformer
        // object to transform our DOM document into a stream of XML text.
        // No arguments to newTransformer() means no XSLT stylesheet
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        // Create the Source and Result objects for the transformation
        DOMSource source = new DOMSource(document); // DOM document
        StreamResult result = new StreamResult(System.out); // to XML text

        // Finally, do the transformation
        transformer.transform(source, result);
    }
}
