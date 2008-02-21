package org.safris.commons.lang;

import com.sun.jmx.snmp.ThreadContext;
import java.lang.reflect.InvocationTargetException;

// FIXME: This is really a ClassLoaderThreadLocal because it uses the
// FIXME: ThreadContext class. Is there an alternative?
public final class ClassLoaderLocal<T>
{
	private final ThreadContext threadContext;

	public ClassLoaderLocal(ClassLoader classLoader)
	{
		try
		{
			final Class<ThreadContext> threadContextClass = (Class<ThreadContext>)classLoader.loadClass(ThreadContext.class.getName());
			threadContext = (ThreadContext)threadContextClass.getDeclaredMethod("getThreadContext").invoke(null);
		}
		catch(InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch(IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch(NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void set(T value)
	{
		threadContext.push("KEY", value);
	}

	public T get()
	{
		return (T)threadContext.get("KEY");
	}
}
