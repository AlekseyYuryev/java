package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.logging.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class BindingErrorHandler implements ErrorHandler
{
	private final Logger logger = Logger.getLogger(RuntimeLoggerName.VALIDATOR);
	private static final BindingErrorHandler instance = new BindingErrorHandler();

	public static BindingErrorHandler getInstance()
	{
		return instance;
	}

	// ignore fatal errors (an exception is guaranteed)
	public void fatalError(SAXParseException e) throws SAXException
	{
	}

	// treat validation errors as fatal
	public void error(SAXParseException e) throws SAXParseException
	{
		final String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
		logger.severe("[" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
		throw e;
	}

	// dump warnings too
	public void warning(SAXParseException e) throws SAXParseException
	{
		final String message = e.getMessage() != null ? " " + e.getMessage() : "";
		logger.warning("[" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
	}

	private BindingErrorHandler()
	{
	}
}
