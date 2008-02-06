package org.safris.xml.generator.processor;

import java.lang.reflect.InvocationTargetException;
import org.safris.commons.logging.Logger;

public abstract class GeneratorError extends Error
{
	protected static final Logger logger = Logger.getAnonymousLogger();

	public GeneratorError()
	{
		super();
	}

	public GeneratorError(String message)
	{
		super(message);
	}

	public GeneratorError(Throwable cause)
	{
		super(cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage());
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}

	public GeneratorError(String message, Throwable cause)
	{
		super(message != null ? message : (cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage()));
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}
}
