package org.safris.commons.el;

public class ExpressionFormatException extends RuntimeException
{
	public ExpressionFormatException()
	{
		super();
	}

	public ExpressionFormatException(String message)
	{
		super(message);
	}

	public ExpressionFormatException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExpressionFormatException(Throwable cause)
	{
		super(cause);
	}
}
