package org.safris.commons.util.logging;

public class ExitSevereError extends Error
{
	private static final Logger logger = Logger.getAnonymousLogger();

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
