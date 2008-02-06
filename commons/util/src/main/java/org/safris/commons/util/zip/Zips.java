package org.safris.commons.util.zip;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.safris.commons.util.zip.CachedFile;

public final class Zips
{
	private static final int BUFFER_SIZE = 65536;

	public static void unzip(final File zipFile, final FileFilter filter, final File targetDir) throws FileNotFoundException, IOException
	{
		// clear target directory:
		if(targetDir.exists())
			targetDir.delete();

		// create new target directory:
		targetDir.mkdirs();

		// read jar-file:
		final ZipFile zip = new ZipFile(zipFile);
		final String targetPath = targetDir.getAbsolutePath() + File.separator;
		final byte[] buffer = new byte[BUFFER_SIZE];
		final Enumeration<? extends ZipEntry> enumeration = zip.entries();
		while(enumeration.hasMoreElements())
		{
			final ZipEntry entry = enumeration.nextElement();
			if(entry.isDirectory())
				continue;

			// do not copy anything from the package cache:
			if(entry.getName().indexOf("package cache") != -1)
				continue;

			final File file = new File(targetPath + entry.getName());
			if(!filter.accept(file))
				continue;

			if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();

			final FileOutputStream out = new FileOutputStream(file);
			final InputStream in = zip.getInputStream(entry);
			int read;
			while((read = in.read(buffer)) != -1)
				out.write(buffer, 0, read);

			in.close();
			out.close();
		}
	}

	public static void add(File zipFile, Collection<CachedFile> files) throws IOException
	{
		// get a temp file
		final File tempFile = File.createTempFile(zipFile.getName(), null);
		// delete it, otherwise you cannot rename your existing zip to it.
		tempFile.delete();

		final File file = new File(zipFile.getName());
		boolean renameOk = file.renameTo(tempFile);
		if(!renameOk)
			throw new RuntimeException("could not rename the file " + file.getAbsolutePath() + " to " + tempFile.getAbsolutePath());

		final byte[] buf = new byte[1024];

		final ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
		final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));

		ZipEntry entry = zin.getNextEntry();
		while(entry != null)
		{
			String name = entry.getName();
			boolean notInFiles = true;
			for(CachedFile fileWrapper : files)
			{
				if(fileWrapper.getPath().equals(name))
				{
					notInFiles = false;
					break;
				}
			}

			if(notInFiles)
			{
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(name));
				// Transfer bytes from the ZIP file to the output file
				int len;
				while((len = zin.read(buf)) > 0)
					out.write(buf, 0, len);
			}

			entry = zin.getNextEntry();
		}

		// Close the streams
		zin.close();
		// Compress the files
		for(CachedFile fil : files)
		{
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(fil.getPath()));
			// Transfer bytes from the file to the ZIP file
			out.write(fil.getBytes());
			// Complete the entry
			out.closeEntry();
		}

		// Complete the ZIP file
		out.close();
		tempFile.delete();
	}

	private Zips()
	{
	}
}
