package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.NamedModel;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class NamedModel extends Model implements Nameable<Model>
{
	private UniqueQName name = null;

	protected NamedModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("name".equals(attribute.getLocalName()))
				name = UniqueQName.getInstance(getTargetNamespace(), attribute.getNodeValue());
		}
	}

	protected final void setName(UniqueQName name)
	{
		this.name = name;
	}

	public UniqueQName getName()
	{
		return name;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(getClass().isInstance(obj)))
			return false;

		final NamedModel namedModel = (NamedModel)obj;
		return (name == null && namedModel.getName() == null) || (name != null && name.equals(namedModel.getName()));
	}

	public int hashCode()
	{
		return (getClass().getName() + toString()).hashCode();
	}

	public String toString()
	{
		if(name == null)
			return super.toString();

		return super.toString() + name.toString();
	}
}
