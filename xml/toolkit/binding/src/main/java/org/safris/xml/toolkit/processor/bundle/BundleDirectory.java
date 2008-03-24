package org.safris.xml.toolkit.processor.bundle;

import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class BundleDirectory implements PipelineDirectory<GeneratorContext,SchemaComposite,Bundle>
{
	private BundleProcessor processor = new BundleProcessor();

	public PipelineEntity<Bundle> getEntity(SchemaComposite entity, Bundle parent)
	{
		return processor;
	}

	public PipelineProcessor<GeneratorContext,SchemaComposite,Bundle> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
