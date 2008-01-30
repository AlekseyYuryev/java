package org.safris.xml.generator.lexer.phase.composite;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;

public final class SchemaCompositePhase extends Phase<SchemaDocument,SchemaComposite> implements ElementModule<SchemaComposite>
{
	protected SchemaCompositePhase()
	{
	}

	public Collection<SchemaComposite> manipulate(Collection<SchemaDocument> documents, BindingContext bindingContext, HandlerDirectory<SchemaDocument, SchemaComposite> directory)
	{
		final Collection<SchemaComposite> selectors = new ArrayList<SchemaComposite>();
		for(SchemaDocument schemaDocument : documents)
			selectors.add(new SchemaModelComposite(schemaDocument));

		return selectors;
	}
}
