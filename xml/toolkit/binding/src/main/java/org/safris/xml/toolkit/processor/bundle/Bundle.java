package org.safris.xml.toolkit.processor.bundle;

import java.io.File;
import org.safris.commons.pipeline.PipelineEntity;

public final class Bundle implements PipelineEntity<Bundle>
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
