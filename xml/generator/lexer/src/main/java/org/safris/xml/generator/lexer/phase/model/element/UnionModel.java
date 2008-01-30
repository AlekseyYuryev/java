package org.safris.xml.generator.lexer.phase.model.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.module.phase.BindingQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class UnionModel extends Model
{
	private final Collection<SimpleTypeModel> memberTypes = new HashSet<SimpleTypeModel>();
	private final Collection<UnionModel> unions = new HashSet<UnionModel>();

	protected UnionModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("memberTypes".equals(attribute.getLocalName()))
				parseMemberTypes(attribute.getNodeValue(), node);
		}
	}

	private final void parseMemberTypes(String memberTypes, Node node)
	{
		final StringTokenizer tokenizer = new StringTokenizer(memberTypes);
		while(tokenizer.hasMoreTokens())
			this.memberTypes.add(SimpleTypeModel.Reference.parseSimpleType(BindingQName.getInstance(parseQNameValue(tokenizer.nextToken(), node))));
	}

	public final Collection<SimpleTypeModel> getMemberTypes()
	{
		return memberTypes;
	}

	public final void addUnion(UnionModel unionModel)
	{
		unions.add(unionModel);
	}

	public final Collection<SimpleTypeModel> getNormalizedMemberTypes()
	{
		final Collection<SimpleTypeModel> allMemberTypes = new ArrayList<SimpleTypeModel>(getMemberTypes());
		for(UnionModel union : unions)
			allMemberTypes.addAll(union.getNormalizedMemberTypes());

		return allMemberTypes;
	}
}
