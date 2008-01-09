package org.safris.xml.generator.lexer.phase.model.element;

import java.util.Arrays;
import java.util.Collection;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ListModel extends Model
{
	private SimpleTypeModel itemType = null;
	private UnionModel unionType = null;

	protected ListModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("itemType".equals(attribute.getLocalName()))
				setItemType(SimpleTypeModel.Reference.parseSimpleType(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
		}
	}

	public final void setItemType(SimpleTypeModel itemType)
	{
		this.itemType = itemType;
	}

	public final void setItemType(UnionModel unionType)
	{
		this.unionType = unionType;
	}

	public final Collection<SimpleTypeModel> getItemType()
	{
		if(unionType != null)
			return unionType.getNormalizedMemberTypes();

		return Arrays.<SimpleTypeModel>asList(itemType);
	}
}
