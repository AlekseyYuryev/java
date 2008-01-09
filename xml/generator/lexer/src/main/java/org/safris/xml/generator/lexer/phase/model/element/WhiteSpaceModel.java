package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.NamedModel;
import org.safris.xml.generator.lexer.schema.attribute.Value;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class WhiteSpaceModel extends NamedModel
{
	private Boolean fixed = null;
	private Value value = null;
	
	protected WhiteSpaceModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("fixed".equals(attribute.getLocalName()))
				fixed = Boolean.parseBoolean(attribute.getNodeValue());
			else if("value".equals(attribute.getLocalName()))
				value = Value.parseValue(attribute.getNodeValue());
		}
	}
	
	public final Boolean getFixed()
	{
		return fixed;
	}
	
	public final Value getValue()
	{
		return value;
	}
}
