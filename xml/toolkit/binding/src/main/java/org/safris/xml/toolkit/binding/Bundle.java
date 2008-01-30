package org.safris.xml.toolkit.binding;

import java.io.File;
import org.safris.xml.generator.processor.phase.ElementModule;

public final class Bundle implements ElementModule<Bundle>
{
	private final File file;

	public Bundle(File file)
	{
		this.file = file;
	}

	public File getFile()
	{
		return file;
	}
}
