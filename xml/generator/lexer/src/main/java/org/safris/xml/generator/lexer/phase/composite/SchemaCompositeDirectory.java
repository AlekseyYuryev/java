package org.safris.xml.generator.lexer.phase.composite;

import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.module.phase.ElementModule;
import org.safris.xml.generator.module.phase.ProcessorDirectory;
import org.safris.xml.generator.module.phase.ModuleProcessor;

public class SchemaCompositeDirectory implements ProcessorDirectory<SchemaDocument,SchemaComposite>
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

	public ModuleProcessor<SchemaDocument, SchemaComposite> getProcessor()
	{
		return phase;
	}

	public void clear()
	{
		phase = null;
	}
}
