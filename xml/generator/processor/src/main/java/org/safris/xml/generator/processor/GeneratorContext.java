package org.safris.xml.generator.processor;

import java.io.File;
import java.io.IOException;

public final class GeneratorContext
{
	private final long manifestLastModified;
	private final File destDir;
	private final boolean explodeJars;
	private final boolean overwrite;

	public GeneratorContext(long manifestLastModified, File destDir, boolean explodeJars, boolean overwrite)
	{
		this.manifestLastModified = manifestLastModified;
		File tempDestDir;
		try
		{
			tempDestDir = destDir.getCanonicalFile();
		}
		catch(IOException e)
		{
			tempDestDir = destDir;
		}
		this.destDir = tempDestDir;
		this.explodeJars = explodeJars;
		this.overwrite = overwrite;
	}

	public long getManifestLastModified()
	{
		return manifestLastModified;
	}

	public File getDestDir()
	{
		return destDir;
	}

	public boolean getExplodeJars()
	{
		return explodeJars;
	}

	public boolean getOverwrite()
	{
		return overwrite;
	}
}
