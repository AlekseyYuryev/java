package org.safris.xml.generator.lexer.document;

import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;

public class SchemaDocumentDirectory implements PipelineDirectory<GeneratorContext,SchemaReference,SchemaDocument>
{
	private final SchemaDocumentProcessor processor = new SchemaDocumentProcessor();

	public SchemaDocumentProcessor getEntity(SchemaReference entity, SchemaDocument parent)
	{
		return processor;
	}

	public PipelineProcessor<GeneratorContext,SchemaReference, SchemaDocument> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
