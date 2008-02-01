package org.safris.xml.generator.lexer.lang;

import org.safris.xml.generator.processor.GeneratorError;

public class LexerError extends GeneratorError
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
