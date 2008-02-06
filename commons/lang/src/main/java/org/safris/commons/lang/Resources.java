package org.safris.commons.lang;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
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

	public static Enumeration<Resource> getResources(String name) throws IOException
	{
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		final Set<URL> history = new HashSet<URL>();
		Enumeration<URL> urls = classLoader.getResources(name);

		final Vector<Resource> resources = new Vector<Resource>(1, 1);
		combineResources(urls, classLoader, history, resources);
		classLoader = Thread.currentThread().getContextClassLoader();
		urls = classLoader.getResources(name);
		combineResources(urls, classLoader, history, resources);

		final Class callerClass = Reflection.getCallerClass(2);
		classLoader = callerClass.getClassLoader();
		urls = classLoader.getResources(name);
		combineResources(urls, classLoader, history, resources);

		return resources.elements();
	}

	private static void combineResources(Enumeration<URL> urls, ClassLoader classLoader, Set<URL> history, Collection<Resource> resources)
	{
		if(urls == null)
			return;

		while(urls.hasMoreElements())
		{
			final URL url = urls.nextElement();
			if(history.contains(url))
				continue;

			history.add(url);
			resources.add(new Resource(url, classLoader));
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

	private Resources()
	{
	}
}
