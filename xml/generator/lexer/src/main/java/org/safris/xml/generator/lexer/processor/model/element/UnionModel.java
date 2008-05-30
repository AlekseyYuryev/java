/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.xml.generator.lexer.processor.model.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringTokenizer;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.lang.UniqueQName;
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
			this.memberTypes.add(SimpleTypeModel.Reference.parseSimpleType(UniqueQName.getInstance(parseQNameValue(tokenizer.nextToken(), node))));
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
