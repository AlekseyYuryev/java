package org.safris.xml.generator.lexer.phase.composite;

import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;

public class SchemaCompositeDirectory implements HandlerDirectory<SchemaDocument,SchemaComposite>
{
	private SchemaCompositePhase phase = null;

	public SchemaCompositeDirectory()
	{
		phase = new SchemaCompositePhase();
	}

	public ElementModule<SchemaComposite> lookup(SchemaDocument key, SchemaComposite parent)
	{
		return phase;
	}

	public Phase<SchemaDocument, SchemaComposite> getPhase()
	{
		return phase;
	}

	public void clear()
	{
		phase = null;
	}
}
