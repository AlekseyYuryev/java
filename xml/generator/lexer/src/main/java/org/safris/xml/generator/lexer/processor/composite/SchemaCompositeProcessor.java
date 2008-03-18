package org.safris.xml.generator.lexer.processor.composite;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.xml.generator.processor.ElementModule;
import org.safris.xml.generator.processor.GeneratorContext;
import org.safris.xml.generator.processor.ModuleProcessor;
import org.safris.xml.generator.processor.ProcessContext;
import org.safris.xml.generator.processor.ProcessorDirectory;

public final class SchemaCompositeProcessor implements ElementModule<SchemaComposite>, ModuleProcessor<GeneratorContext,SchemaDocument,SchemaComposite>
{
	public Collection<SchemaComposite> process(Collection<SchemaDocument> documents, GeneratorContext processContext, ProcessorDirectory<GeneratorContext,SchemaDocument,SchemaComposite> directory)
	{
		final Collection<SchemaComposite> selectors = new ArrayList<SchemaComposite>();
		for(SchemaDocument schemaDocument : documents)
			selectors.add(new SchemaModelComposite(schemaDocument));

		return selectors;
	}
}
