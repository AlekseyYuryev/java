package org.safris.commons.jci;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.safris.commons.exec.Processes;
import org.safris.commons.io.Files;
import org.safris.commons.io.Streams;
import org.safris.commons.lang.Resources;
import org.safris.commons.util.jar.Jar;
import org.safris.commons.util.zip.CachedFile;
import org.safris.commons.util.zip.Zips;

public final class JavaCompiler
{
	private final Collection<File> classpathFiles;
	private final File destDir;
	private final Jar destJar;

	public JavaCompiler(File destDir, Collection<File> classpath)
	{
		this.classpathFiles = classpath;
		if(destDir == null)
			throw new IllegalArgumentException("dir cannot be null");

		if(destDir.isFile())
			throw new IllegalArgumentException(destDir + " is a file!");

		this.destDir = destDir;
		this.destJar = null;
	}

	public JavaCompiler(Jar destJar, Collection<File> classpath)
	{
		this.classpathFiles = classpath;
		this.destDir = null;
		if(destJar == null)
			throw new IllegalArgumentException("jar cannot be null");

		if(destDir.isDirectory())
			throw new IllegalArgumentException(destDir + " is a directory!");

		this.destJar = destJar;
	}

	public void compile(Collection<File> javaSources) throws Exception
	{
		if(destDir == null)
			toJar(destJar, javaSources);
		else
			toDir(destDir, javaSources);
	}

	private void toJar(Jar destJar, Collection<File> javaSources) throws Exception
	{
		if(javaSources == null || javaSources.size() == 0)
			return;

		final File tempDir = new File(UUID.randomUUID().toString());
		toDir(tempDir, javaSources);
		final FileFilter fileFilter = new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return true;
			}
		};

		Files.deleteAllOnExit(tempDir, fileFilter);
		try
		{
			toDir(tempDir, javaSources);
			final Collection<File> files = Files.listAll(tempDir);
			final Collection<CachedFile> selected = new ArrayList<CachedFile>();
			for(File file : files)
			{
				if(!file.isFile() || !file.getName().endsWith(".class"))
					continue;

				selected.add(new CachedFile(file.getPath().substring(tempDir.getPath().length() + 1), Files.getBytes(file)));
			}

			Zips.add(destJar.getFile(), selected);
		}
		finally
		{
			Files.deleteAllOnExit(tempDir, fileFilter);
		}
	}

	private void toDir(File destDir, Collection<File> javaSources) throws Exception
	{
		if(javaSources == null || javaSources.size() == 0)
			return;

		if(!destDir.exists())
			if(!destDir.mkdirs())
				throw new IllegalArgumentException("Could not create directory " + destDir);

		String classpath = "";
		if(classpathFiles != null)
			for(File classpathFile : classpathFiles)
				if(classpathFile != null)
					classpath += classpathFile + File.pathSeparator;

		classpath += System.getProperty("java.class.path");
		final File locationBase = Resources.getLocationBase(JavaCompiler.class);
		if(locationBase != null)
			classpath = locationBase.getAbsolutePath() + File.pathSeparator + classpath;

		final File tempFile = File.createTempFile("xml", ".tmp");
		final FileOutputStream out = new FileOutputStream(tempFile);
		for(File javaSource : javaSources)
		{
			out.write(javaSource.getAbsolutePath().getBytes());
			out.write('\n');
		}
		out.close();

		int i = 0;
		final String[] args = new String[8];
		args[i++] = "javac";
		args[i++] = "-Xmaxwarns";
		args[i++] = "0";
		args[i++] = "-d";
		args[i++] = destDir.getAbsolutePath();
		args[i++] = "-cp";
		args[i++] = classpath;
		args[i++] = "@" + tempFile.getAbsolutePath();

		final Process process = Processes.forkSync(System.in, System.out, System.err, args);
		Streams.pipe(process.getInputStream(), System.out);
		Streams.pipe(process.getErrorStream(), System.err);
		process.waitFor();
		tempFile.delete();
	}
}
