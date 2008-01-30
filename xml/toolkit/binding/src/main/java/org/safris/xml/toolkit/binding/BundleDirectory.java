package org.safris.xml.toolkit.binding;

import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.safris.xml.toolkit.binding.Bundle;

public class BundleDirectory implements ProcessorDirectory<SchemaComposite,Bundle>
{
	private BundleProcessor phase = null;

	public BundleDirectory()
	{
		phase = new BundleProcessor();
	}

	public ElementModule<Bundle> lookup(SchemaComposite key, Bundle parent)
	{
		return phase;
	}

	public ModuleProcessor<SchemaComposite, Bundle> getProcessor()
	{
		return phase;
	}


	public void clear()
	{
		phase = null;
	}
}
