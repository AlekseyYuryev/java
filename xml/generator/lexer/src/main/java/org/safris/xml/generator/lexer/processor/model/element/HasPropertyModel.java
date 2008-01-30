package org.safris.xml.generator.lexer.processor.model.element;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.NamedModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class HasPropertyModel extends NamedModel
{
	private String value = null;
	
	protected HasPropertyModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("value".equals(attribute.getLocalName()))
				value = attribute.getNodeValue();
		}
	}
	
	public final String getValue()
	{
		return value;
	}
}
