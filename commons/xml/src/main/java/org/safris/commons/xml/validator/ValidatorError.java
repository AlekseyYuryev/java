package org.safris.commons.xml.validator;

import org.safris.commons.xml.XMLError;

public class ValidatorError extends XMLError
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
