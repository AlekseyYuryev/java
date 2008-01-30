package org.safris.xml.generator.lexer.document;

import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class SchemaDocumentDirectory implements ProcessorDirectory<SchemaReference,SchemaDocument>
{
	private final SchemaDocumentProcessor processor = new SchemaDocumentProcessor();

	public SchemaDocumentProcessor getModule(SchemaReference module, SchemaDocument parent)
	{
		return processor;
	}

	public ModuleProcessor<SchemaReference, SchemaDocument> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
