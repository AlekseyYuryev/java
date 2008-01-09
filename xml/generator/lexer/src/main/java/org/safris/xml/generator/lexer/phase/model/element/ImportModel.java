package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ImportModel extends Model
{
	private String namespace = null;
	private String schemaLocation = null;

	protected ImportModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("namespace".equals(attribute.getLocalName()))
				namespace = attribute.getNodeValue();
			else if("schemaLocation".equals(attribute.getLocalName()))
				schemaLocation = attribute.getNodeValue();
		}
	}

	public final String getNamespace()
	{
		return namespace;
	}

	public final String getSchemaLocation()
	{
		return schemaLocation;
	}
}
