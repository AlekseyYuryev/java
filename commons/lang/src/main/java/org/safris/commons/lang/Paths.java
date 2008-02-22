package org.safris.commons.lang;

public final class Paths
{
	public static boolean isAbsolute(String path)
	{
		if(path == null)
			throw new NullPointerException();

		return path.charAt(0) == '/' || (Character.isLetter(path.charAt(0)) && path.charAt(1) == ':' && path.charAt(2) == '\\' && Character.isLetter(path.charAt(3)));
	}

	public static String canonicalize(String path)
	{
		if(path == null)
			return null;

		if(path.endsWith(".."))
			path = path + "/";

		int index;
		while((index = path.indexOf("/./")) != -1)
			path = path.substring(0, index) + path.substring(index + 2);

		// Process "/../" correctly. This probably isn't very efficient in
		// the general case, but it's probably not bad most of the time.
		while((index = path.indexOf("/../")) != -1)
		{
			// Strip of the previous directory - if it exists.
			final int previous = path.lastIndexOf('/', index - 1);
			if(previous != -1)
				path = path.substring(0, previous) + path.substring(index + 3);
			else
				break;
		}

		return path;
	}

	public static String relativePath(String dir, String file)
	{
		if(dir == null || file == null)
			return null;

		final String filePath = Paths.canonicalize(file);
		final String dirPath = Paths.canonicalize(dir);

		if(!filePath.startsWith(dirPath))
			return filePath;

		if(filePath.length() == dirPath.length())
			return "";

		return filePath.substring(dirPath.length() + 1);
	}

	public static String getParent(String url)
	{
		if(url == null)
			return null;

		url = canonicalize(url);
		final int separator = url.lastIndexOf('/');
		if(separator > 0)
			return url.substring(0, separator);

		return url;
	}

	public static String getName(String url)
	{
		if(url == null)
			return null;

		if(url.endsWith("/"))
			url = url.substring(0, url.length() - 1);

		final int separator = url.lastIndexOf('/');
		if(separator != -1)
			return url.substring(separator + 1);

		return url;
	}

	private Paths()
	{
	}
}
