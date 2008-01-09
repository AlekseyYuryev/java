package org.safris.commons.util.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class DOMParsers
{
	private static final ErrorHandler defaultErrorHandler = new ErrorHandler()
	{
		// ignore fatal errors (an exception is guaranteed)
		public void fatalError(SAXParseException exception) throws SAXException
		{
		}

		// treat validation errors as fatal
		public void error(SAXParseException e) throws SAXParseException
		{
			final String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
			System.err.println("ERROR [" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
			throw e;
		}

		// dump warnings too
		public void warning(SAXParseException e) throws SAXParseException
		{
			final String message = e.getMessage() != null ? " " + e.getMessage() : "";
			System.err.println("WARNING [" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
		}
	};

	public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException
	{
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setIgnoringComments(true);
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
		documentBuilderFactory.setValidating(false);

		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		documentBuilder.setErrorHandler(defaultErrorHandler);

		return documentBuilder;
	}

	private DOMParsers()
	{
	}
}
