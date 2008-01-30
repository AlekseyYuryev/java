package org.safris.xml.generator.lexer.document;

import org.safris.xml.generator.lexer.document.SchemaDocumentProcessor;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class SchemaDocumentDirectory implements ProcessorDirectory<SchemaReference,SchemaDocument>
{
	private SchemaDocumentProcessor phase = null;

	public SchemaDocumentDirectory()
	{
		this.phase = new SchemaDocumentProcessor();
	}

	public SchemaDocumentProcessor lookup(SchemaReference key, SchemaDocument parent)
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
