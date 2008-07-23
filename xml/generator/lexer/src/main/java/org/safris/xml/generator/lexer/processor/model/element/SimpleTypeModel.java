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
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.Undefineable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.DocumentableModel;
import org.safris.xml.generator.lexer.processor.model.EnumerableModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.PatternableModel;
import org.safris.xml.generator.lexer.processor.model.RedefineableModel;
import org.safris.xml.generator.lexer.processor.model.TypeableModel;
import org.safris.xml.generator.lexer.schema.attribute.Final;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SimpleTypeModel<T extends SimpleTypeModel> extends AliasModel implements DocumentableModel, EnumerableModel, PatternableModel, RedefineableModel<T>, TypeableModel<T>
{
	private final LinkedHashSet<EnumerationModel> enumerations = new LinkedHashSet<EnumerationModel>();
	private final LinkedHashSet<PatternModel> patterns = new LinkedHashSet<PatternModel>();

	private T redefine = null;
	private SimpleTypeModel superType = null;
	private Collection<SimpleTypeModel> itemType = null;

	private boolean restriction = false;
	private boolean list = false;

	private Final _final = null;

	protected SimpleTypeModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("final".equals(attribute.getLocalName()))
				_final = Final.parseFinal(attribute.getNodeValue());
		}
	}

	public final void setRedefine(T redefine)
	{
		this.redefine = redefine;
	}

	public final T getRedefine()
	{
		return redefine;
	}

	public final void setSuperType(SimpleTypeModel superType)
	{
		if(!this.equals(superType))
			this.superType = superType;
	}

	public SimpleTypeModel getSuperType()
	{
		return superType;
	}

	public final void setItemTypes(Collection<SimpleTypeModel> itemType)
	{
		this.itemType = itemType;
	}

	public final Collection<SimpleTypeModel> getItemTypes()
	{
		return itemType;
	}

	public final void setRestriction(boolean isRestriction)
	{
		this.restriction = isRestriction;
	}

	public final boolean isRestriction()
	{
		return restriction;
	}

	public final void setList(boolean list)
	{
		this.list = list;
	}

	public final boolean isList()
	{
		return list;
	}

	public final Final getFinal()
	{
		return _final;
	}

	public final void addEnumeration(EnumerationModel enumeration)
	{
		if(!enumerations.contains(enumeration))
			enumerations.add(enumeration);
	}

	public final LinkedHashSet<EnumerationModel> getEnumerations()
	{
		return enumerations;
	}

	public final void addPattern(PatternModel pattern)
	{
		this.patterns.add(pattern);
	}

	public final LinkedHashSet<PatternModel> getPatterns()
	{
		return patterns;
	}

	public final static class Reference extends SimpleTypeModel implements Referenceable
	{
		private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

		public static Reference parseSimpleType(UniqueQName name)
		{
			Reference type = all.get(name);
			if(type != null)
				return type;

			type = new Reference(null);
			type.setName(name);
			Reference.all.put(name, type);
			return type;
		}

		protected Reference(Model parent)
		{
			super(null, parent);
		}
	}

	public final static class Undefined extends SimpleTypeModel implements Undefineable
	{
		private static final Map<UniqueQName,Undefined> all = new HashMap<UniqueQName,Undefined>();

		public static Undefined parseSimpleType(UniqueQName name)
		{
			Undefined type = all.get(name);
			if(type != null)
				return type;

			type = new Undefined(null);
			type.setName(name);
			Undefined.all.put(name, type);
			return type;
		}

		protected Undefined(Model parent)
		{
			super(null, parent);
		}
	}
}
