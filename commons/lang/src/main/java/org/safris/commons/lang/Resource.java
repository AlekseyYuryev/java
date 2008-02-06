package org.safris.commons.lang;

import java.net.URL;

public final class Resource
{
	private final URL url;
	private final ClassLoader classLoader;

	public Resource(URL url, ClassLoader classLoader)
	{
		this.url = url;
		this.classLoader = classLoader;
	}

	public URL getURL()
	{
		return url;
	}

	public ClassLoader getClassLoader()
	{
		return classLoader;
	}
}
