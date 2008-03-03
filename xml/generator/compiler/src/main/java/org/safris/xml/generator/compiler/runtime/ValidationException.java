package org.safris.xml.generator.compiler.runtime;

import org.safris.xml.generator.compiler.runtime.BindingException;

public class ValidationException extends BindingException
{
	public ValidationException()
	{
		super();
	}

	public ValidationException(String message)
	{
		super(message);
	}

	public ValidationException(Throwable cause)
	{
		super(cause.getMessage());
		setStackTrace(cause.getStackTrace());
	}

	public ValidationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
