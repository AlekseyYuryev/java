package org.safris.xml.generator.lexer.processor.reference;

import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;

public class SchemaReferenceDirectory implements PipelineDirectory<GeneratorContext,SchemaReference,SchemaReference>
{
	private final SchemaReferenceProcessor schemaReferenceProcessor = new SchemaReferenceProcessor();

	public PipelineEntity<SchemaReference> getEntity(SchemaReference entity, SchemaReference parent)
	{
		return schemaReferenceProcessor;
	}

	public PipelineProcessor<GeneratorContext,SchemaReference,SchemaReference> getProcessor()
	{
		return schemaReferenceProcessor;
	}

	public void clear()
	{
	}
}
