package org.safris.xml.toolkit.processor.timestamp;

import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;
import org.safris.xml.toolkit.processor.bundle.Bundle;

public class TimestampDirectory implements PipelineDirectory<GeneratorContext,Bundle,Bundle>
{
	private TimestampProcessor processor = new TimestampProcessor();

	public PipelineEntity<Bundle> getEntity(Bundle entity, Bundle parent)
	{
		return processor;
	}

	public PipelineProcessor<GeneratorContext,Bundle,Bundle> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
