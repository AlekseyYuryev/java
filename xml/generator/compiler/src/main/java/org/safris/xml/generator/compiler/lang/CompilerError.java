package org.safris.xml.generator.compiler.lang;

import org.safris.commons.xml.XMLError;

public class CompilerError extends XMLError
{
	public CompilerError()
	{
		super();
	}

	public CompilerError(String message)
	{
		super(message);
	}

	public CompilerError(Throwable cause)
	{
		super(cause);
	}

	public CompilerError(String message, Throwable cause)
	{
		super(message, cause);
	}
}
