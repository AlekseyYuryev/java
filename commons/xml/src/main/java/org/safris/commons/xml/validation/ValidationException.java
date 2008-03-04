package org.safris.commons.xml.validation;

public class ValidationException extends Exception
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
