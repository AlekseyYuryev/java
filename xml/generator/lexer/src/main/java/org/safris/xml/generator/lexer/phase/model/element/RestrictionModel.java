package org.safris.xml.generator.lexer.phase.model.element;

import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.lang.LexerError;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RestrictionModel extends Model
{
	private SimpleTypeModel base = null;

	protected RestrictionModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("base".equals(attribute.getLocalName()))
			{
				final Node parentNode = node.getParentNode();
				if(parentNode.getLocalName().contains("complex"))
					base = ComplexTypeModel.Reference.parseComplexType(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node)));
				else if(parentNode.getLocalName().contains("simple"))
					base = SimpleTypeModel.Reference.parseSimpleType(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node)));
				else
					throw new LexerError("whoa, schema error?");
			}
		}
	}

	public final void setBase(SimpleTypeModel base)
	{
		this.base = base;
	}

	public final SimpleTypeModel getBase()
	{
		return base;
	}
}
