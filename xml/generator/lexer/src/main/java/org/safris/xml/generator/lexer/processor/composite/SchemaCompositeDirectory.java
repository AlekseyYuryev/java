package org.safris.xml.generator.lexer.processor.composite;

import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;

public class SchemaCompositeDirectory implements PipelineDirectory<GeneratorContext,SchemaDocument,SchemaComposite>
{
	private SchemaCompositeProcessor processor = new SchemaCompositeProcessor();

	public PipelineEntity<SchemaComposite> getEntity(SchemaDocument entity, SchemaComposite parent)
	{
		return processor;
	}

	public PipelineProcessor<GeneratorContext,SchemaDocument,SchemaComposite> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
