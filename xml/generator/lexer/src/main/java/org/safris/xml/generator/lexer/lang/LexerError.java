package org.safris.xml.generator.lexer.lang;

import java.lang.reflect.InvocationTargetException;

public class LexerError extends Error
{
	public LexerError()
	{
		super();
	}

	public LexerError(String message)
	{
		super(message);
	}

	public LexerError(Throwable cause)
	{
		super(cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage());
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}

	public LexerError(String message, Throwable cause)
	{
		super(message != null ? message : (cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage()));
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}
}
