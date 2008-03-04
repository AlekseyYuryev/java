package org.safris.commons.xml.validator;

public class ValidatorError extends Error
{
	public ValidatorError()
	{
		super();
	}

	public ValidatorError(String message)
	{
		super(message);
	}

	public ValidatorError(Throwable cause)
	{
		super(cause);
	}

	public ValidatorError(String message, Throwable cause)
	{
		super(message, cause);
	}
}
