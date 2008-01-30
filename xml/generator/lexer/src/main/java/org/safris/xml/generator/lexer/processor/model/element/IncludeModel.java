package org.safris.xml.generator.lexer.processor.model.element;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class IncludeModel extends Model
{
	private String schemaLocation = null;

	protected IncludeModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("schemaLocation".equals(attribute.getLocalName()))
				schemaLocation = attribute.getNodeValue();
		}
	}

	public final String getSchemaLocation()
	{
		return schemaLocation;
	}
}
