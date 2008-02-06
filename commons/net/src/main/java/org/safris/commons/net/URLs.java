package org.safris.commons.net;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import org.safris.commons.lang.Paths;

public final class URLs
{
	// FIXME: Make sure that this is correct!!!
	private static final Pattern WINDOWS_PATH = Pattern.compile("[a-zA-Z]*:\\\\");

	public static boolean isAbsolute(String path)
	{
		if(path == null)
			throw new NullPointerException();

		if(path.length() == 0)
			return false;

		if(path.charAt(0) == '/')
			return true;

		if(WINDOWS_PATH.matcher(path).find())
			return true;

		return false;
	}

	public static URL makeUrlFromPath(String path) throws MalformedURLException
	{
		URL url;
		final int protocol = path.indexOf(":/");
		if(protocol != -1)
			url = new URL(path);
		else
			url = new File(path).toURL();

		return URLs.canonicalizeURL(url);
	}

	public static URL makeUrlFromPath(String basedir, String path) throws MalformedURLException
	{
		if(path == null)
			return null;

		URL url;
		final int protocol = path.indexOf(":/");
		if(protocol != -1)
			url = new URL(path);
		else if(basedir != null)
		{
			if(path.length() == 0)
				url = new File(basedir).toURL();
			else if(path.charAt(0) != File.separatorChar)
				url = new File(basedir, path).toURL();
			else
				return null;
		}
		else
			url = new File(path).toURL();

		return URLs.canonicalizeURL(url);
	}

	public static String toExternalForm(URL url) throws MalformedURLException
	{
		if(url == null)
			return null;

		try
		{
			return url.toURI().toASCIIString();
		}
		catch(URISyntaxException e)
		{
			throw new MalformedURLException(url.toString() + e.getMessage());
		}
	}

	public static boolean exists(URL url)
	{
		try
		{
			if("file".equals(url.getProtocol()))
				return new File(url.getFile()).exists();

			url.openStream().close();
		}
		catch(IOException e)
		{
			return false;
		}

		return true;
	}

	public static URL canonicalizeURL(URL url) throws MalformedURLException
	{
		if(url == null)
			return null;

		final String path = Paths.canonicalize(url.toString());
		if(path == null)
			return null;

		return new URL(path);
	}

	public static String getName(String url)
	{
		final int separator = url.lastIndexOf('/');
		if(separator != -1)
			return url.substring(separator + 1);

		final int colon = url.lastIndexOf(':');
		if(separator != -1)
			return url.substring(colon + 1);

		return url;
	}

	public static String getName(URL url)
	{
		return getName(url.toString());
	}

	public static String getParent(String url)
	{
		final int separator = url.lastIndexOf('/');
		if(separator != -1)
			return url.substring(0, separator);

		return url;
	}

	public static String getParent(URL url)
	{
		return getParent(url.toString());
	}

	private URLs()
	{
	}
}
