package org.safris.xml.generator.compiler.lang;

import org.safris.commons.util.logging.ExitSevereError;
import org.safris.commons.util.logging.Logger;
import org.safris.xml.generator.lexer.lang.LexerError;

public class CompilerError extends LexerError
{
	private static final java.util.logging.Logger logger = Logger.getLogger(ExitSevereError.class.getName()).logger();

	public CompilerError()
	{
		super();
	}

	public CompilerError(String message)
	{
		super(message);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(message);
	}

	public CompilerError(Throwable cause)
	{
		super(cause);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(cause.getMessage());
	}

	public CompilerError(String message, Throwable cause)
	{
		super(message, cause);
		setStackTrace(new StackTraceElement[0]);
		logger.severe(message + " " + cause.getMessage());
	}
}
