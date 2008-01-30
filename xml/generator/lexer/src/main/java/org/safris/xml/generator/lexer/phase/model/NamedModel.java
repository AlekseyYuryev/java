package org.safris.xml.generator.lexer.phase.model;

import org.safris.xml.generator.lexer.phase.Nameable;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.NamedModel;
import org.safris.xml.generator.processor.BindingQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class NamedModel extends Model implements Nameable<Model>
{
	private BindingQName name = null;

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
				name = BindingQName.getInstance(getTargetNamespace(), attribute.getNodeValue());
		}
	}

	protected final void setName(BindingQName name)
	{
		this.name = name;
	}

	public BindingQName getName()
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
