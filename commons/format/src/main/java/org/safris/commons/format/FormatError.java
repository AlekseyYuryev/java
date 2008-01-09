package org.safris.commons.format;

public class FormatError extends Error
{
	public FormatError()
	{
		super();
	}
	
	public FormatError(String message)
	{
		super(message);
	}
	
	public FormatError(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public FormatError(Throwable cause)
	{
		super(cause);
	}
}
