package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AppinfoModel extends Model
{
	private String source = null;

	protected AppinfoModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("source".equals(attribute.getLocalName()))
				source = attribute.getNodeValue();
		}
	}

	public final String getSource()
	{
		return source;
	}

	public String toString()
	{
		return super.toString().replace(TO_STRING_DELIMITER, "source=\"" + source + "\"");
	}
}
