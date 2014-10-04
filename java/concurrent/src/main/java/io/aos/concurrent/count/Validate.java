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
import java.io.FileReader;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Validate {
	public static void main(String... args) throws IOException {
		File documentFile = new File(args[0]); // 1st arg is document
		File schemaFile = new File(args[1]); // 2nd arg is schema

		// Get a parser to parse W3C schemas. Note use of javax.xml package
		// This package contains just one class of constants.
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// Now parse the schema file to create a Schema object
		Schema schema = null;
		try {
			schema = factory.newSchema(schemaFile);
		} catch (SAXException e) {
			fail(e);
		}

		// Get a Validator object from the Schema.
		Validator validator = schema.newValidator();

		// Get a SAXSource object for the document
		// We could use a DOMSource here as well
		SAXSource source = new SAXSource(new InputSource(new FileReader(documentFile)));

		// Now validate the document
		try {
			validator.validate(source);
		} catch (SAXException e) {
			fail(e);
		}

		System.err.println("Document is valid");
	}

	static void fail(SAXException e) {
		if (e instanceof SAXParseException) {
			SAXParseException spe = (SAXParseException) e;
			System.err.printf("%s:%d:%d: %s%n", spe.getSystemId(), spe.getLineNumber(), spe.getColumnNumber(),
			        spe.getMessage());
		} else {
			System.err.println(e.getMessage());
		}
		System.exit(1);
	}
}
