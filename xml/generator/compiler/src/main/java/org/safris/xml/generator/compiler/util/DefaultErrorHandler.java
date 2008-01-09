package org.safris.xml.generator.compiler.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultErrorHandler implements ErrorHandler
{
	// ignore fatal errors (an exception is guaranteed)
	public void fatalError(SAXParseException e) throws SAXException
	{
	}
	
	// treat validation errors as fatal
	public void error(SAXParseException e) throws SAXParseException
	{
		String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
		System.out.println("ERROR [" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
		throw e;
	}
	
	// dump warnings too
	public void warning(SAXParseException e) throws SAXParseException
	{
		String message = e.getMessage() != null ? " " + e.getMessage() : "";
		System.out.println("WARNING [" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
	}
}
