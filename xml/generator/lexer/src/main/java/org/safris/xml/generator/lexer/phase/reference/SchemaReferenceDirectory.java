package org.safris.xml.generator.lexer.phase.reference;

import org.safris.xml.generator.processor.phase.ElementModule;
import org.safris.xml.generator.processor.phase.ProcessorDirectory;
import org.safris.xml.generator.processor.phase.ModuleProcessor;

public class SchemaReferenceDirectory implements ProcessorDirectory<SchemaReference,SchemaReference>
{
	private SchemaReferencePhase schemaReferencePhase = null;

	public SchemaReferenceDirectory()
	{
		schemaReferencePhase = new SchemaReferencePhase();
	}

	public ElementModule<SchemaReference> lookup(SchemaReference key, SchemaReference parent)
	{
		return schemaReferencePhase;
	}

	public ModuleProcessor<SchemaReference,SchemaReference> getProcessor()
	{
		return schemaReferencePhase;
	}


	public void clear()
	{
		schemaReferencePhase = null;
	}
}
