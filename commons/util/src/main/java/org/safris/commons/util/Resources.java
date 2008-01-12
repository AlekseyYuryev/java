package org.safris.commons.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import sun.reflect.Reflection;

public final class Resources
{
	public static File getLocationBase(Class clazz)
	{
		final URL url = clazz.getResource(clazz.getSimpleName() + ".class");
		if(url == null)
			return null;

		String classFile = url.getFile();
		final int colon = classFile.indexOf(':');
		final int bang = classFile.indexOf('!');
		if(bang != -1 && colon != -1)
			classFile = classFile.substring(colon + 1, bang);
		else
			classFile = classFile.substring(0, classFile.length() - clazz.getName().length() - 7);

		return new File(classFile);
	}

	public static Resource getResource(String name)
	{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL url = classLoader.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		classLoader = Thread.currentThread().getContextClassLoader();
		url = classLoader.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		final Class callerClass = Reflection.getCallerClass(2);
		classLoader = callerClass.getClassLoader();
		url = classLoader.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		return null;
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

	private Resources()
	{
	}
}
