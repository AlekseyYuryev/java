package org.safris.xml.generator.lexer.document;

import org.safris.xml.generator.lexer.document.SchemaDocumentPhase;
import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.lexer.phase.reference.SchemaReference;
import org.safris.xml.generator.processor.phase.ProcessorDirectory;
import org.safris.xml.generator.processor.phase.ModuleProcessor;

public class SchemaDocumentDirectory implements ProcessorDirectory<SchemaReference,SchemaDocument>
{
	private SchemaDocumentPhase phase = null;

	public SchemaDocumentDirectory()
	{
		this.phase = new SchemaDocumentPhase();
	}

	public SchemaDocumentPhase lookup(SchemaReference key, SchemaDocument parent)
	{
		return phase;
	}

	public ModuleProcessor<SchemaReference, SchemaDocument> getProcessor()
	{
		return phase;
	}

	public void clear()
	{
		phase = null;
	}
}
