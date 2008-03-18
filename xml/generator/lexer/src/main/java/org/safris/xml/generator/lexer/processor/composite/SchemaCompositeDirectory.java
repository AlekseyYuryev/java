package org.safris.xml.generator.lexer.processor.composite;

import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessorDirectory;

public class SchemaCompositeDirectory implements ProcessorDirectory<GeneratorContext,SchemaDocument,SchemaComposite>
{
	private SchemaCompositeProcessor processor = new SchemaCompositeProcessor();

	public ElementModule<SchemaComposite> getModule(SchemaDocument module, SchemaComposite parent)
	{
		return processor;
	}

	public ModuleProcessor<GeneratorContext,SchemaDocument,SchemaComposite> getProcessor()
	{
		return processor;
	}

	public void clear()
	{
	}
}
