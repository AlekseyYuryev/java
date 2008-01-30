package org.safris.xml.generator.lexer.phase.composite;

import org.safris.xml.generator.lexer.phase.document.SchemaDocument;
import org.safris.xml.generator.lexer.phase.model.element.SchemaModel;

public class SchemaModelComposite implements SchemaComposite
{
	private final SchemaDocument schemaDocument;
	private SchemaModel schemaModel = null;

	public SchemaModelComposite(SchemaDocument schemaDocument)
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
}
