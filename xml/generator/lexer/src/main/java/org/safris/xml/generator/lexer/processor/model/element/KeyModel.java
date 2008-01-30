package org.safris.xml.generator.lexer.processor.model.element;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class KeyModel extends Model
{
	private String xpath = null;
	
	protected KeyModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("xpath".equals(attribute.getLocalName()))
				xpath = attribute.getNodeValue();
		}
	}
	
	public final String getXpath()
	{
		return xpath;
	}
}
