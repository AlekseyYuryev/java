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

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.model.AttributableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.NamedModel;
import org.safris.xml.generator.lexer.processor.model.RedefineableModel;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;
import org.safris.xml.generator.lexer.processor.model.element.AttributeModel;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeGroupModel extends NamedModel implements AttributableModel, RedefineableModel<AttributeGroupModel>, ReferableModel<AttributeGroupModel>
{
	private final LinkedHashSet<AttributeModel> attributes = new LinkedHashSet<AttributeModel>();
	private AttributeGroupModel ref = null;
	private AttributeGroupModel redefine = null;

	protected AttributeGroupModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("ref".equals(attribute.getLocalName()))
				ref = AttributeGroupModel.Reference.parseAttributeGroup(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node)));
		}
	}

	public final void setRedefine(AttributeGroupModel redefine)
	{
		this.redefine = redefine;
	}

	public final AttributeGroupModel getRedefine()
	{
		return redefine;
	}

	public final void addAttribute(AttributeModel attribute)
	{
		attributes.add(attribute);
	}

	public final void addAllAttributes(Collection<AttributeModel> attributes)
	{
		attributes.addAll(attributes);
	}

	public final LinkedHashSet<AttributeModel> getAttributes()
	{
		return attributes;
	}

	public final void setRef(AttributeGroupModel ref)
	{
		this.ref = ref;
	}

	public final AttributeGroupModel getRef()
	{
		return ref;
	}

	public String toString()
	{
		return super.toString().replace(TO_STRING_DELIMITER, "ref=\"" + ref + "\"");
	}

	public static class Reference extends AttributeGroupModel implements Referenceable
	{
		private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseAttributeGroup(UniqueQName name)
		{
			Reference type = all.get(name);
			if(type != null)
				return type;

			type = new Reference(null);
			type.setName(name);
			Reference.all.put(name, type);
			return type;
		}
	}
}
