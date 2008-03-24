package org.safris.xml.generator.lexer.processor.composite;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.processor.GeneratorContext;
import org.safris.xml.generator.lexer.processor.composite.SchemaComposite;
import org.safris.xml.generator.lexer.processor.document.SchemaDocument;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.commons.pipeline.PipelineProcessor;
import org.safris.commons.pipeline.PipelineDirectory;

public final class SchemaCompositeProcessor implements PipelineEntity<SchemaComposite>, PipelineProcessor<GeneratorContext,SchemaDocument,SchemaComposite>
{
	public Collection<SchemaComposite> process(GeneratorContext pipelineContext, Collection<SchemaDocument> documents, PipelineDirectory<GeneratorContext,SchemaDocument,SchemaComposite> directory)
	{
		final Collection<SchemaComposite> selectors = new ArrayList<SchemaComposite>();
		for(SchemaDocument schemaDocument : documents)
			selectors.add(new SchemaModelComposite(schemaDocument));

		return selectors;
	}
}
