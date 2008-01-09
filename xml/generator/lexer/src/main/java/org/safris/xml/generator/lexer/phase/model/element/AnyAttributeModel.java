package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.xml.generator.lexer.phase.model.AnyableModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.schema.attribute.Namespace;
import org.safris.xml.generator.lexer.schema.attribute.ProcessContents;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AnyAttributeModel extends AttributeModel implements AnyableModel
{
	private Namespace namespace = Namespace.ANY;
	private ProcessContents processContents = ProcessContents.STRICT;

	protected AnyAttributeModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("namespace".equals(attribute.getLocalName()))
				namespace = Namespace.parseNamespace(attribute.getNodeValue());
			else if("processContents".equals(attribute.getLocalName()))
				processContents = ProcessContents.parseProcessContents(attribute.getNodeValue());
		}
	}

	public final Namespace getNamespace()
	{
		return namespace;
	}

	public final ProcessContents getProcessContents()
	{
		return processContents;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof AnyAttributeModel))
			return false;

		final AnyAttributeModel anyAttribute = (AnyAttributeModel)obj;
		return namespace.equals(anyAttribute.namespace) && processContents.equals(anyAttribute.processContents);
	}
}
