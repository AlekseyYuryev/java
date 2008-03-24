package org.safris.commons.xml;

import java.lang.reflect.InvocationTargetException;

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
		super(cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage());
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}

	public XMLError(String message, Throwable cause)
	{
		super(message != null ? message : (cause instanceof InvocationTargetException ? cause.getCause().getMessage() : cause.getMessage()));
		if(cause instanceof InvocationTargetException)
			initCause(cause.getCause());
		else
			initCause(cause);

		setStackTrace(getCause().getStackTrace());
	}
}
