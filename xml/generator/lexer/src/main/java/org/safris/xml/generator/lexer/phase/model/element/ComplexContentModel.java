package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.MixableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ComplexContentModel extends Model implements MixableModel
{
	private Boolean mixed = null;
	
	protected ComplexContentModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("mixed".equals(attribute.getLocalName()))
				mixed = Boolean.parseBoolean(attribute.getNodeValue());
		}
	}
	
	public final Boolean getMixed()
	{
		return mixed;
	}
}
