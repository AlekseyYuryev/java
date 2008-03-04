package org.safris.commons.xml.validator;

import java.io.File;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.junit.Test;
import org.safris.commons.xml.dom.DOMParsers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.Assert.*;

public class DefaultValidatorTest
{
	public static void main(String[] args) throws Exception
	{
		new DefaultValidatorTest().testSAXParser();
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
			new DefaultValidator().parse(element);
		}
		catch(ValidationException e)
		{
			if(e.getMessage().startsWith(XMLSchemaResolver.class.getName() + " cannot be cast to " + XMLEntityResolver.class.getName()))
				fail(e.getMessage());
			else if(e.getCause() == null || e.getCause().getMessage() == null || !e.getCause().getMessage().startsWith("cvc-elt.1: Cannot find the declaration of element"))
				throw e;
		}
	}
}
