package org.safris.xml.toolkit.binding;

import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class BundleDirectory implements ProcessorDirectory<SchemaComposite,Bundle>
{
	private BundleProcessor processor = new BundleProcessor();

	public ElementModule<Bundle> getModule(SchemaComposite module, Bundle parent)
	{
		return processor;
	}

	public ModuleProcessor<SchemaComposite, Bundle> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
