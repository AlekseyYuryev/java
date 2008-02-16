package org.safris.xml.toolkit.processor.timestamp;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import org.safris.commons.io.Files;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampProcessor implements ElementModule<Bundle>, ModuleProcessor<Bundle,Bundle>
{
	private static final FileFilter fileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname != null && pathname.isFile();
		}
	};

	private static final FileFilter dirFileFilter = new FileFilter()
	{
		public boolean accept(File pathname)
		{
			return pathname != null && pathname.isDirectory();
		}
	};

	protected TimestampProcessor()
	{
	}

	public Collection<Bundle> process(Collection<Bundle> documents, GeneratorContext generatorContext, ProcessorDirectory<Bundle,Bundle> directory)
	{
		// Get the earliest lastModified time of all the files
		long lastModified = Long.MAX_VALUE;
		for(File file : Files.listAll(generatorContext.getDestDir(), fileFilter))
			if(file.lastModified() < lastModified)
				lastModified = file.lastModified();

		// Set the lastModified time of all directories to just before the value from above
		for(File dir : Files.listAll(generatorContext.getDestDir(), dirFileFilter))
			dir.setLastModified(lastModified - 100);

		return null;
	}
}
