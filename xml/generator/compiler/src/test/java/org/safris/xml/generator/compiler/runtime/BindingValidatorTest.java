/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.compiler.runtime;

import java.io.File;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.junit.Test;
import org.safris.commons.xml.dom.DOMParsers;
import org.safris.commons.xml.validator.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.Assert.*;

public class BindingValidatorTest
{
	public static void main(String[] args) throws Exception
	{
		new BindingValidatorTest().testSAXParser();
	}

	/**
	 * This test verifies that the correct implementation of the SAXParser is used
	 * within the validator. A SAXParser implementation other than the default
	 * will cause a ClassCastException stating that
	 * org.safris.xml.generator.compiler.runtime.XMLSchemaResolver cannot be cast
	 * to org.apache.xerces.xni.parser.XMLEntityResolver.
	 *
	 * @exception Exception If any <code>Exception</code> is thrown.
	 */
	@Test
	public void testSAXParser() throws Exception
	{
		System.setProperty("org.xml.sax.driver", SAXParser.class.getName());
		final Document document = DOMParsers.newDocumentBuilder().parse(new File("src/test/resources/xml/empty.xml"));
		if(document == null)
			fail("document == null");

		final Element element = document.getDocumentElement();
		try
		{
			new BindingValidator().parse(element);
		}
		catch(ValidationException e)
		{
			if(e.getMessage().startsWith(BindingEntityResolver.class.getName() + " cannot be cast to " + XMLEntityResolver.class.getName()))
				fail(e.getMessage());
			else if(e.getCause() == null || e.getCause().getMessage() == null || !e.getCause().getMessage().startsWith("cvc-elt.1: Cannot find the declaration of element"))
				throw e;
		}
	}
}
