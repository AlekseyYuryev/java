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

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof Resource))
			return false;

		final Resource resource = (Resource)obj;
		return url != null ? url.equals(resource.url) && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null) : resource.url == null && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null);
	}

	public int hashCode()
	{
		return url.hashCode() * 3 + classLoader.hashCode();
	}
}
