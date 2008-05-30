package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.xml.XMLError;

public class BindingError extends XMLError
{
	public BindingError()
	{
		super();
	}

	public BindingError(String message)
	{
		super(message);
	}

	public BindingError(Throwable cause)
	{
		super(cause);
		init(cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName(), cause);
	}

	public BindingError(String message, Throwable cause)
	{
		super(message, cause);
		init(message != null ? message : (cause.getMessage() != null ? cause.getMessage() : cause.getClass().getSimpleName()), cause);
	}
}
