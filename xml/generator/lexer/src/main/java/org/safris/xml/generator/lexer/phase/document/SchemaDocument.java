package org.safris.xml.generator.lexer.phase.document;

import java.net.URL;
import java.util.Collection;
import org.safris.xml.generator.lexer.phase.reference.SchemaReference;
import org.safris.xml.generator.module.phase.ElementModule;
import org.w3c.dom.Document;

public class SchemaDocument implements ElementModule
{
	private final SchemaReference schemaReference;
	private final Document document;
	private Collection<URL> includes;

	public SchemaDocument(SchemaReference schemaReference, Document document)
	{
		this.schemaReference = schemaReference;
		this.document = document;
	}

	public SchemaReference getSchemaReference()
	{
		return schemaReference;
	}

	public Document getDocument()
	{
		return document;
	}

	public Collection<URL> getIncludes()
	{
		return includes;
	}

	public void setIncludes(Collection<URL> includes)
	{
		this.includes = includes;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof SchemaDocument))
			return false;

		final SchemaDocument schemaDocument = (SchemaDocument)obj;
		return schemaReference.equals(schemaDocument.schemaReference);
	}

	public int hashCode()
	{
		return ("schemaDocument" + schemaReference.toString()).hashCode();
	}
}
