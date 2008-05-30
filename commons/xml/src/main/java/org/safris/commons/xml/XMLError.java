package org.safris.commons.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class XMLError extends Error
{
	private final Object lock = new Object();

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
		if(cause instanceof InvocationTargetException)
			init(cause.getCause().getMessage(), cause.getCause());
		else
			init(cause.getMessage(), cause);
	}

	public XMLError(String message, Throwable cause)
	{
		if(cause instanceof InvocationTargetException)
			init(message != null ? message : cause.getCause().getMessage(), cause.getCause());
		else
			init(cause.getMessage(), cause);
	}

	protected final void init(String message, Throwable cause)
	{
		setMessage(message);
		overwriteCause(cause.getCause());
		setStackTrace(cause.getStackTrace());
	}

	protected final void overwriteCause(Throwable cause)
	{
        if(cause == this)
            throw new IllegalArgumentException("Self-causation not permitted");

		try
		{
			synchronized(lock)
			{
				final Field detailMessageField = Throwable.class.getDeclaredField("cause");
				detailMessageField.setAccessible(true);
				detailMessageField.set(this, cause);
			}
		}
		catch(SecurityException e)
		{
		}
		catch(NoSuchFieldException e)
		{
		}
		catch(IllegalAccessException e)
		{
		}
		catch(IllegalArgumentException e)
		{
		}
	}

	protected final void setMessage(String message)
	{
		try
		{
			synchronized(lock)
			{
				final Field detailMessageField = Throwable.class.getDeclaredField("detailMessage");
				detailMessageField.setAccessible(true);
				detailMessageField.set(this, message);
			}
		}
		catch(SecurityException e)
		{
		}
		catch(NoSuchFieldException e)
		{
		}
		catch(IllegalAccessException e)
		{
		}
		catch(IllegalArgumentException e)
		{
		}
	}
}
