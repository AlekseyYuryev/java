package org.safris.commons.xml;

public class XMLError extends Error
{
	public XMLError()
	{
		super();
	}

	public XMLError(String message)
	{
		super(message);
	}

	public XMLError(Throwable cause)
	{
		super(cause);
	}

	public XMLError(String message, Throwable cause)
	{
		super(message, cause);
	}
}
