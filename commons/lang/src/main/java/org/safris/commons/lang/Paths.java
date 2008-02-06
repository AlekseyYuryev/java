package org.safris.commons.lang;

public final class Paths
{
	public static String canonicalize(String path)
	{
		if(path == null)
			return null;

		int index;
		while((index = path.indexOf("/./")) >= 0)
			path = path.substring(0, index) + path.substring(index + 2);

		// Process "/../" correctly. This probably isn't very efficient in
		// the general case, but it's probably not bad most of the time.
		while((index = path.indexOf("/../")) >= 0)
		{
			// Strip of the previous directory - if it exists.
			final int previous = path.lastIndexOf('/', index - 1);
			if(previous >= 0)
				path = path.substring(0, previous) + path.substring(index + 3);
			else
				break;
		}

		return path;
	}

	private Paths()
	{
	}
}
