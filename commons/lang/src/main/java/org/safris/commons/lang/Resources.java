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

package org.safris.commons.lang;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
		if(clazz == null)
			return null;

		final Resource resource = getResource(clazz.getName().replace('.', '/') + ".class");
		if(resource == null)
			return null;

		final URL url = resource.getURL();
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
		if(name == null || name.length() == 0)
			return null;

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		URL url = classLoader.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		classLoader = Thread.currentThread().getContextClassLoader();
		url = classLoader.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		final Class callerClass = Reflection.getCallerClass(2);
		url = callerClass.getResource(name);
		if(url != null)
			return new Resource(url, classLoader);

		return null;
	}

	public static Enumeration<Resource> getResources(String name) throws IOException
	{
		if(name == null || name.length() == 0)
			return null;

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

	private Resources()
	{
	}
}
