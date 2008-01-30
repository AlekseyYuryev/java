package org.safris.xml.generator.lexer.processor.composite;

import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class SchemaCompositeDirectory implements ProcessorDirectory<SchemaDocument,SchemaComposite>
{
	private SchemaCompositeProcessor processor = new SchemaCompositeProcessor();

	public ElementModule<SchemaComposite> lookup(SchemaDocument key, SchemaComposite parent)
	{
		return processor;
	}

	public ModuleProcessor<SchemaDocument, SchemaComposite> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
