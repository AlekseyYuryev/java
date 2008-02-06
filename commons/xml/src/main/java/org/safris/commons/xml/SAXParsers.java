package org.safris.commons.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public final class SAXParsers
{
	private static final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

	static
	{
		saxParserFactory.setNamespaceAware(true);
	}

	public static SAXParser newSAXParser() throws ParserConfigurationException, SAXException
	{
		return saxParserFactory.newSAXParser();
	}

	private SAXParsers()
	{
	}
}
