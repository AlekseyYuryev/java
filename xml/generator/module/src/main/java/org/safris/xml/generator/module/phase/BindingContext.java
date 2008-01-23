package org.safris.xml.generator.module.phase;

import java.io.File;
import java.io.IOException;

public final class BindingContext
{
	private File destDir;
	private boolean explodeJars;
	private boolean overwrite;

	public BindingContext(File destDir, boolean explodeJars, boolean overwrite)
	{
		this.destDir = destDir;
		this.explodeJars = explodeJars;
		this.overwrite = overwrite;
	}

	public BindingContext()
	{
	}

	public void getStaticReferenceManager()
	{
	}

	public void setDestDir(File destDir)
	{
		try
		{
			this.destDir = destDir.getCanonicalFile();
		}
		catch(IOException e)
		{
			this.destDir = destDir;
		}
	}

	public File getDestDir()
	{
		return destDir;
	}

	public void setExplodeJars(boolean explodeJars)
	{
		this.explodeJars = explodeJars;
	}

	public boolean getExplodeJars()
	{
		return explodeJars;
	}

	public void setOverwrite(boolean overwrite)
	{
		this.overwrite = overwrite;
	}

	public boolean getOverwrite()
	{
		return overwrite;
	}
}
