package org.safris.xml.generator.lexer.processor.document;

import java.net.URL;
import java.util.Collection;
import org.safris.commons.pipeline.PipelineEntity;
import org.safris.xml.generator.lexer.processor.reference.SchemaReference;
import org.w3c.dom.Document;

public class SchemaDocument implements PipelineEntity
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

		final SchemaDocument that = (SchemaDocument)obj;
		return schemaReference.equals(that.schemaReference);
	}

	public int hashCode()
	{
		return (schemaReference != null ? schemaReference.hashCode() : -1) + (document != null ? document.hashCode() : -1);
	}
}
