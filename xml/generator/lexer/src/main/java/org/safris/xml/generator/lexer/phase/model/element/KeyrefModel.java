package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class KeyrefModel extends Model
{
	private String xpath = null;
	private String refer = null;
	
	protected KeyrefModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("xpath".equals(attribute.getLocalName()))
				xpath = attribute.getNodeValue();
			else if("refer".equals(attribute.getLocalName()))
				refer = attribute.getNodeValue();
		}
	}
	
	public final String getXpath()
	{
		return xpath;
	}
	
	public final String getRefer()
	{
		return refer;
	}
}
