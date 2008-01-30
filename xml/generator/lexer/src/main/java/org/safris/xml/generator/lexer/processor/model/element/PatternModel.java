package org.safris.xml.generator.lexer.processor.model.element;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class PatternModel extends Model
{
	private static String formatForJava(String string)
	{
		return string.replace("\\", "\\\\");
	}
	
	private String value = null;
	
	protected PatternModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("value".equals(attribute.getLocalName()))
				value = formatForJava(attribute.getNodeValue());
		}
	}
	
	public final String getValue()
	{
		return value;
	}
}
