package org.safris.xml.toolkit.binding;

import java.util.ArrayList;
import java.util.Collection;
import org.safris.commons.util.xml.SchemaDocument;
import org.safris.xml.generator.lexer.phase.composite.SchemaComposite;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.Phase;

public final class Translation extends Phase
{
	private static final Translation instance = new Translation();

	public static Translation instance()
	{
		return instance;
	}

	public Collection<? extends Phase> manipulate(Collection documents, BindingContext share)
	{
		final Collection<SchemaComposite> selectors = new ArrayList<SchemaComposite>();
		for(SchemaDocument schemaDocument : (Collection<SchemaDocument>)documents)
			selectors.add(new SchemaComposite(schemaDocument));

		return selectors;
	}

	private Translation()
	{
	}
}
