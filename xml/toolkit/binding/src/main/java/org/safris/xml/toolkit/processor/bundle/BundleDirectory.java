package org.safris.xml.toolkit.processor.bundle;

import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class BundleDirectory implements ProcessorDirectory<GeneratorContext,SchemaComposite,Bundle>
{
	private BundleProcessor processor = new BundleProcessor();

	public ElementModule<Bundle> getModule(SchemaComposite module, Bundle parent)
	{
		return processor;
	}

	public ModuleProcessor<GeneratorContext,SchemaComposite,Bundle> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
