package org.safris.commons.xml.validator;

import org.safris.commons.xml.XMLException;

public class ValidationException extends XMLException
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
