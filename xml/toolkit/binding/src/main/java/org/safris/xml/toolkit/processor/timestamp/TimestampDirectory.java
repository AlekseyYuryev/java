package org.safris.xml.toolkit.processor.timestamp;

import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampDirectory implements ProcessorDirectory<GeneratorContext,Bundle,Bundle>
{
	private TimestampProcessor processor = new TimestampProcessor();

	public ElementModule<Bundle> getModule(Bundle module, Bundle parent)
	{
		return processor;
	}

	public ModuleProcessor<GeneratorContext,Bundle,Bundle> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
