package org.safris.commons.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import sun.reflect.Reflection;

public final class ClassLoaders
{
	public static boolean isClassLoaded(ClassLoader classLoader, String name)
	{
		if(classLoader == null)
			throw new IllegalArgumentException("classLoader == null");

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

	public static URL[] getClassPath()
	{
		final Collection<URL> urls = new ArrayList<URL>();
		urls.addAll(Arrays.asList(((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs()));
		urls.addAll(Arrays.asList(((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs()));
		final Class callerClass = Reflection.getCallerClass(2);
		final ClassLoader classLoader = callerClass.getClassLoader();
		if(classLoader instanceof URLClassLoader)
			urls.addAll(Arrays.asList(((URLClassLoader)classLoader).getURLs()));

		return urls.toArray(new URL[urls.size()]);
	}

	private ClassLoaders()
	{
	}
}
