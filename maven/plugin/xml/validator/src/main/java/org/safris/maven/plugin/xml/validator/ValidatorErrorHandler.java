package org.safris.maven.plugin.xml.validator;

import java.util.HashMap;
import java.util.Map;
import org.apache.maven.plugin.logging.Log;
import org.safris.maven.plugin.xml.validator.ValidatorErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class ValidatorErrorHandler implements ErrorHandler
{
	private static final Map<Log,ValidatorErrorHandler> instances = new HashMap<Log,ValidatorErrorHandler>();
	private static final Object lock = new Object();
	private static ValidatorErrorHandler instance = null;

	public static ValidatorErrorHandler getInstance(Log log)
	{
		if(log == null)
			return getInstance();

		ValidatorErrorHandler instance = instances.get(log);
		if(instance != null)
			return instance;

		synchronized(instances)
		{
			instance = instances.get(log);
			if(instance != null)
				return instance;

			instances.put(log, instance = new ValidatorErrorHandler(log));
		}

		return instance;
	}

	public static ValidatorErrorHandler getInstance()
	{
		if(instance != null)
			return instance;

		synchronized(lock)
		{
			if(instance != null)
				return instance;

			instance = new ValidatorErrorHandler();
		}

		return instance;
	}

	private final Log log;

	// ignore fatal errors (an exception is guaranteed)
	public void fatalError(SAXParseException e) throws SAXException
	{
	}

	// treat validation errors as fatal
	public void error(SAXParseException e) throws SAXParseException
	{
		throw e;
	}

	// dump warnings too
	public void warning(SAXParseException e) throws SAXParseException
	{
		if(e.getMessage() != null && e.getMessage().startsWith("schema_reference.4"))
			throw e;

		if(log != null)
			log.warn(e.getMessage());
		else
			System.err.println("[WARNING] " + e.getMessage());
	}

	public ValidatorErrorHandler(Log log)
	{
		this.log = log;
	}

	public ValidatorErrorHandler()
	{
		this.log = null;
	}
}
