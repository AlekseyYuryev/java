package org.safris.xml.generator.compiler.lang;

import org.safris.xml.generator.processor.GeneratorError;

public class CompilerError extends GeneratorError
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
