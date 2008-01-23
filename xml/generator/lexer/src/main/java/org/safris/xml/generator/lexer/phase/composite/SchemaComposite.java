package org.safris.xml.generator.lexer.phase.composite;

import java.util.Collection;
import org.safris.commons.util.xml.SchemaDocument;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;
import org.safris.xml.generator.module.phase.BindingContext;
import org.safris.xml.generator.module.phase.Phase;

public class SchemaComposite extends Phase
{
	private final SchemaDocument schemaDocument;
	private SchemaModel schemaModel = null;

	public SchemaComposite(SchemaDocument schemaDocument)
	{
		this.schemaDocument = schemaDocument;
	}

	public SchemaDocument getSchemaDocument()
	{
		return schemaDocument;
	}

	public void setSchemaModel(SchemaModel schemaModel)
	{
		this.schemaModel = schemaModel;
	}

	public SchemaModel getSchemaModel()
	{
		return schemaModel;
	}

	public Collection manipulate(Collection documents, BindingContext share)
	{
		throw new IllegalStateException("Should not be called!");
	}
}
