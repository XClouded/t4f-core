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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathEvaluator {
    public static void main(String... args)
    throws ParserConfigurationException, XPathExpressionException,
           org.xml.sax.SAXException, java.io.IOException
    {
    String documentName = args[0];
    String expression = args[1];

    // Parse the document to a DOM tree
    // XPath can also be used with a SAX InputSource
    DocumentBuilder parser =
        DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = parser.parse(new java.io.File(documentName));
    
    // Get an XPath object to evaluate the expression
    XPath xpath = XPathFactory.newInstance().newXPath();

    System.out.println(xpath.evaluate(expression, doc));

    // Or evaluate the expression to obtain a DOM NodeList of all matching
    // nodes.  Then loop through each of the resulting nodes
    NodeList nodes = (NodeList)xpath.evaluate(expression, doc,
                          XPathConstants.NODESET);
    for(int i = 0, n = nodes.getLength(); i < n; i++) {
        Node node = nodes.item(i);
        System.out.println(node);
    }
    }
}
