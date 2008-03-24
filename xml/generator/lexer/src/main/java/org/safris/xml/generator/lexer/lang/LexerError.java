package org.safris.xml.generator.lexer.lang;

import org.safris.commons.xml.XMLError;

public class LexerError extends XMLError
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
		super(cause);
	}

	public LexerError(String message, Throwable cause)
	{
		super(message, cause);
	}
}
