package org.safris.xml.generator.lexer.processor;

import java.io.File;
import java.io.IOException;
import org.safris.commons.pipeline.PipelineContext;

public final class GeneratorContext implements PipelineContext
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
