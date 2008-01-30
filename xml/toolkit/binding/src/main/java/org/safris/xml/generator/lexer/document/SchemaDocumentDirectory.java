package org.safris.xml.generator.lexer.document;

import org.safris.xml.generator.lexer.document.SchemaDocumentPhase;
import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.lexer.phase.reference.SchemaReference;
import org.safris.xml.generator.module.phase.HandlerDirectory;
import org.safris.xml.generator.module.phase.Phase;

public class SchemaDocumentDirectory implements HandlerDirectory<SchemaReference,SchemaDocument>
{
	private SchemaDocumentPhase phase = null;

	public SchemaDocumentDirectory()
	{
		this.phase = new SchemaDocumentPhase();
	}

	public SchemaDocumentPhase lookup(SchemaReference key, SchemaDocument parent)
	{
		return phase;
	}

	public Phase<SchemaReference, SchemaDocument> getPhase()
	{
		return phase;
	}

	public void clear()
	{
		phase = null;
	}
}
