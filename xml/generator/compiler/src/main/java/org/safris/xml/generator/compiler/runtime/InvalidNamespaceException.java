package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.xml.validation.ValidationException;

public class InvalidNamespaceException extends ValidationException
{
	public InvalidNamespaceException()
	{
		super();
	}

	public InvalidNamespaceException(String message)
	{
		super(message);
	}

	public InvalidNamespaceException(Throwable cause)
	{
		super(cause);
	}

	public InvalidNamespaceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
