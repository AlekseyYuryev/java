package org.safris.commons.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ClassLoaders
{
	public static boolean isClassLoaded(ClassLoader classLoader, String name)
	{
		try
		{
			final Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
			method.setAccessible(true);
			return method.invoke(classLoader, name) != null;
		}
		catch(InvocationTargetException e)
		{
			return false;
		}
		catch(NoSuchMethodException e)
		{
			return false;
		}
		catch(IllegalAccessException e)
		{
			throw new SecurityException(e);
		}
	}

	private ClassLoaders()
	{
	}
}
