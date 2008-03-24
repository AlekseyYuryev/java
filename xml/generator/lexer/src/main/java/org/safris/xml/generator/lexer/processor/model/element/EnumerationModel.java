package org.safris.xml.generator.lexer.processor.model.element;

import javax.xml.namespace.QName;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class EnumerationModel extends Model
{
	private QName value = null;

	protected EnumerationModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("value".equals(attribute.getLocalName()))
				value = parseQNameValue(attribute.getNodeValue(), node);
		}
	}

	public EnumerationModel(QName value)
	{
		super(null, null);
		this.value = value;
	}

	public final QName getValue()
	{
		return value;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(!(obj instanceof EnumerationModel))
			return false;

		EnumerationModel equals = (EnumerationModel)obj;
		return (getValue() == null && equals.getValue() == null) || (getValue() != null && getValue().equals(equals.getValue()));
	}

	public int hashCode()
	{
		return getValue().hashCode();
	}
}
