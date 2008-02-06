package org.safris.commons.lang;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This utility class is for loading all of the classes in a package.
 *
 * @author Seva Safris
 * @version 1.3
 */
public abstract class PackageLoader extends ClassLoader
{
	private static final PackageLoader instance = new PackageLoader(){};
	private static final FileFilter classFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname.getName().endsWith(".class");
		}
	};

	public static PackageLoader getSystemPackageLoader()
	{
		return instance;
	}

	protected PackageLoader()
	{
	}

	public void loadPackage(String name) throws ClassNotFoundException, PackageNotFoundException
	{
		if(name == null || name.length() == 0)
			throw new PackageNotFoundException(name);

		// Translate the package name into an absolute path
		final String path;
		final char firstChar = name.charAt(0);
		if(firstChar == '/' || firstChar == '.')
			path = name.substring(1).replace('.', '/');
		else
			path = name.replace('.', '/');

		Enumeration<Resource> resources = null;
		try
		{
			resources = Resources.getResources(path);
		}
		catch(IOException e)
		{
			throw new ResourceException(e.getMessage(), e);
		}

		if(resources == null)
			throw new PackageNotFoundException(name);

		while(resources.hasMoreElements())
		{
			final Resource resource = resources.nextElement();
			final URL url = resource.getURL();
			final ClassLoader classLoader = resource.getClassLoader();
			synchronized(classLoader)
			{
				String decodedUrl;
				try
				{
					decodedUrl = URLDecoder.decode(url.getPath(), "UTF-8");
				}
				catch(UnsupportedEncodingException e)
				{
					decodedUrl = url.getPath();
				}

				final File directory = new File(decodedUrl);
				if(directory.exists())
				{
					// Get the list of the files contained in the package
					final File[] files = directory.listFiles(classFileFilter);
					String className = null;
					for(File file : files)
					{
						className = file.getName().substring(0, file.getName().length() - 6);
						Class.forName(name + "." + className, true, classLoader);
					}
				}
				else
				{
					final JarURLConnection jarURLConnection;
					final JarFile jarFile;
					try
					{
						jarURLConnection = (JarURLConnection)url.openConnection();
						jarFile = jarURLConnection.getJarFile();
					}
					catch(IOException e)
					{
						throw new PackageNotFoundException(name, e);
					}

					final String entryName = jarURLConnection.getEntryName();
					final Enumeration<JarEntry> enumeration = jarFile.entries();
					String zipEntryName;
					String className;
					while(enumeration.hasMoreElements())
					{
						zipEntryName = enumeration.nextElement().getName();
						if(!zipEntryName.startsWith(entryName) || zipEntryName.lastIndexOf(entryName + "/") > entryName.length() || !zipEntryName.endsWith(".class"))
							continue;

						className = zipEntryName.substring(0, zipEntryName.length() - 6);
						if(className.charAt(0) == '/')
							className = className.substring(1);

						className = className.replace('/', '.');
						Class.forName(className, true, classLoader);
					}
				}
			}
		}
	}
}
