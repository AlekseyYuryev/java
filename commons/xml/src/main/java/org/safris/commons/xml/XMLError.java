/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
