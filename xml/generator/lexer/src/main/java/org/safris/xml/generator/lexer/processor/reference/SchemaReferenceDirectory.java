package org.safris.xml.generator.lexer.processor.reference;

import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class SchemaReferenceDirectory implements ProcessorDirectory<GeneratorContext,SchemaReference,SchemaReference>
{
	private final SchemaReferenceProcessor schemaReferenceProcessor = new SchemaReferenceProcessor();

	public ElementModule<SchemaReference> getModule(SchemaReference module, SchemaReference parent)
	{
		return schemaReferenceProcessor;
	}

	public ModuleProcessor<GeneratorContext,SchemaReference,SchemaReference> getProcessor()
	{
		return schemaReferenceProcessor;
	}

	public void clear()
	{
	}
}
