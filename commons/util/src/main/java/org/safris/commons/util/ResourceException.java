package org.safris.commons.util;

public class ResourceException extends RuntimeException
{
	public ResourceException()
	{
		super();
	}

	public ResourceException(String message)
	{
		super(message);
	}

	public ResourceException(Throwable cause)
	{
		super(cause);
	}

	public ResourceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
