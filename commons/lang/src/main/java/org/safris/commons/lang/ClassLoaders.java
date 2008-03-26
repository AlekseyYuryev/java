package org.safris.commons.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import sun.misc.URLClassPath;
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
		final Collection<URL> urls = new HashSet<URL>();
		urls.addAll(Arrays.asList(((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs()));
		urls.addAll(Arrays.asList(((URLClassLoader)Thread.currentThread().getContextClassLoader()).getURLs()));
		final Class callerClass = Reflection.getCallerClass(2);
		final ClassLoader classLoader = callerClass.getClassLoader();
		try
		{
			// TODO: I dont know why, but when running forked JUnit tests
			// TODO: the classpath is not available by calling the getURLs
			// TODO: method. Instead, it is hidden deep inside the URLClassPath
			final Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
			ucpField.setAccessible(true);
			final URLClassPath ucp = (URLClassPath)ucpField.get(classLoader);
			final Field lmapField = URLClassPath.class.getDeclaredField("lmap");
			lmapField.setAccessible(true);
			final Map<URL,Object> lmap = (Map<URL,Object>)lmapField.get(ucp);
			urls.addAll(lmap.keySet());
		}
		catch(Exception e)
		{
			// TODO: Oh well, try the regular approach
			urls.addAll(Arrays.asList(((URLClassLoader)classLoader).getURLs()));
		}

		return urls.toArray(new URL[urls.size()]);
	}

	private ClassLoaders()
	{
	}
}
