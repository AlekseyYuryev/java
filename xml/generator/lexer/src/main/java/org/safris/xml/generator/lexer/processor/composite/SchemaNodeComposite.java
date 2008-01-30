package org.safris.xml.generator.lexer.processor.composite;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.Node;

public class SchemaNodeComposite implements SchemaComposite
{
	private final Node node;

	public SchemaNodeComposite(Node node)
	{
		this.node = node;
	}

	public Node getNode()
	{
		return node;
	}
}
