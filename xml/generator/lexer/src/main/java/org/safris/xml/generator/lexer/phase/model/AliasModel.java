package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.model.DocumentableModel;
import org.safris.xml.generator.lexer.phase.model.element.DocumentationModel;
import org.w3c.dom.Node;

public abstract class AliasModel extends NamedModel implements DocumentableModel
{
	private DocumentationModel documentation = null;

	protected AliasModel(Node node, Model parent)
	{
		super(node, parent);
	}

	public final void setDocumentation(DocumentationModel documentation)
	{
		this.documentation = documentation;
	}

	public final DocumentationModel getDocumentation()
	{
		return documentation;
	}
}
