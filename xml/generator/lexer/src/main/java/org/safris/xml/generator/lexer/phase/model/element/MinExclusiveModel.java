package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class MinExclusiveModel extends Model
{
	private Boolean fixed = false;
	private String value = null;
	
	protected MinExclusiveModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("fixed".equals(attribute.getLocalName()))
				fixed = Boolean.parseBoolean(attribute.getNodeValue());
			else if("value".equals(attribute.getLocalName()))
				value = attribute.getNodeValue();
		}
	}
	
	public final Boolean getFixed()
	{
		return fixed;
	}
	
	public final String getValue()
	{
		return value;
	}
}
