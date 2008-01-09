package org.safris.commons.util.logging;

import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.logging.Logger;

public class ExitSevereError extends Error
{
	private static final java.util.logging.Logger logger = Logger.getLogger(ExitSevereError.class.getName()).logger();

	public ExitSevereError()
	{
		super();
		setStackTrace(new StackTraceElement[0]);
	}

	public ExitSevereError(String message)
	{
		super(message);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(message);
	}

	public ExitSevereError(Throwable cause)
	{
		super(cause);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(cause.getMessage());
	}

	public ExitSevereError(String message, Throwable cause)
	{
		super(message, cause);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(message + " " + cause.getMessage());
	}
}
