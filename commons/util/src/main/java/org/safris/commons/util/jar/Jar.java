package org.safris.commons.util.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public final class Jar
{
	private final File jarFile;
	private final FileOutputStream stream;
	private final JarOutputStream out;
	private final Collection<JarEntry> entries = new HashSet<JarEntry>();

	public Jar(File file) throws IOException
	{
		this.jarFile = file;
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		this.stream = new FileOutputStream(file);
		this.out = new JarOutputStream(stream, new Manifest());
	}

	public File getFile()
	{
		return jarFile;
	}

	public void addEntry(String fileName, byte[] bytes) throws IOException
	{
		synchronized(out)
		{
			// Add archive entry
			final JarEntry jarEntry = new JarEntry(fileName);
			entries.add(jarEntry);
			jarEntry.setTime(System.currentTimeMillis());

			// Write file to archive
			try
			{
				out.putNextEntry(jarEntry);
				out.write(bytes);
			}
			catch(IOException e)
			{
				if("no current ZIP entry".equals(e.getMessage()) || "Stream closed".equals(e.getMessage()))
					return;
			}
		}
	}

	public Collection<JarEntry> getEntries()
	{
		return entries;
	}

	public void close() throws IOException
	{
		synchronized(out)
		{
			out.close();
			stream.close();
		}
	}
}
