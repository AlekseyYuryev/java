package org.safris.xml.generator.lexer.phase.reference;

import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;

public class SchemaReferenceDirectory implements HandlerDirectory<SchemaReference,SchemaReference>
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

	public Phase<SchemaReference,SchemaReference> getPhase()
	{
		return schemaReferencePhase;
	}


	public void clear()
	{
		schemaReferencePhase = null;
	}
}
