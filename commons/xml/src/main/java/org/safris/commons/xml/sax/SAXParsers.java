package org.safris.commons.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

public final class SAXParsers
{
	public static SAXParser createParser() throws SAXException
	{
		// Create a specific SAXParser so that it works with our XMLSchemaResolver
		return new SAXParser(XMLReaderFactory.createXMLReader(com.sun.org.apache.xerces.internal.parsers.SAXParser.class.getName()));
	}

	private SAXParsers()
	{
	}
}
