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

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Formable;
import org.safris.xml.generator.lexer.processor.Referenceable;
import org.safris.xml.generator.lexer.processor.model.AliasModel;
import org.safris.xml.generator.lexer.processor.model.Model;
import org.safris.xml.generator.lexer.processor.model.ReferableModel;
import org.safris.xml.generator.lexer.processor.model.RestrictableModel;
import org.safris.xml.generator.lexer.processor.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeModel extends SimpleTypeModel<SimpleTypeModel> implements Formable<Model>, ReferableModel<AttributeModel>, RestrictableModel<AttributeModel>
{
	private QName _default = null;
	private QName fixed = null;
	private Form form = null;
	private Use use = Use.OPTIONAL;
	private Form formDefault = null;
	private AttributeModel ref = null;
	private AliasModel restrictionOwner = null;
	private AttributeModel restriction = null;

	public AttributeModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("default".equals(attribute.getLocalName()))
				_default = parseQNameValue(attribute.getNodeValue(), node);
			else if("fixed".equals(attribute.getLocalName()))
				fixed = parseQNameValue(attribute.getNodeValue(), node);
			else if("form".equals(attribute.getLocalName()))
				form = Form.parseForm(attribute.getNodeValue());
			else if("ref".equals(attribute.getLocalName()))
				setRef(AttributeModel.Reference.parseAttribute(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
			else if("type".equals(attribute.getLocalName()))
				setSuperType(SimpleTypeModel.Reference.parseSimpleType(UniqueQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
			else if("use".equals(attribute.getLocalName()))
				use = Use.parseUse(attribute.getLocalName());
		}
	}

	public final void setRestriction(AttributeModel restriction)
	{
		this.restriction = restriction;
	}

	public final AttributeModel getRestriction()
	{
		return restriction;
	}

	public final AliasModel getRestrictionOwner()
	{
		return restrictionOwner;
	}

	public final void setRestrictionOwner(AliasModel restrictionOwner)
	{
		this.restrictionOwner = restrictionOwner;
	}

	public final AttributeModel getRef()
	{
		return ref;
	}

	public final void setRef(AttributeModel ref)
	{
		this.ref = ref;
	}

	public final UniqueQName getName()
	{
		if(ref != null)
			return ref.getName();

		return super.getName();
	}

	public final SimpleTypeModel getSuperType()
	{
		if(ref != null)
			return ref.getSuperType();

		return super.getSuperType();
	}

	public final QName getDefault()
	{
		return _default;
	}

	public final QName getFixed()
	{
		return fixed;
	}

	public final Form getForm()
	{
		return form;
	}

	public final Use getUse()
	{
		return use;
	}

	public final void setFormDefault(Form formDefault)
	{
		this.formDefault = formDefault;
	}

	public final Form getFormDefault()
	{
		return formDefault;
	}

	public boolean equals(Object obj)
	{
		final boolean equals = super.equals(obj);
		if(!equals)
			return false;

		final AttributeModel that = (AttributeModel)obj;
		return (getRef() == null && that.getRef() == null) || (getRef() != null && getRef().equals(that.getRef()));
	}

	public int hashCode()
	{
		return (getClass().getName() + toString()).hashCode();
	}

	public String toString()
	{
		if(getName() == null)
		{
			if(getRef() == null)
				return null;

			return getRef().toString();
		}

		return getName().toString();
	}

	public final static class Reference extends AttributeModel implements Referenceable
	{
		private static final Map<UniqueQName,Reference> all = new HashMap<UniqueQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseAttribute(UniqueQName name)
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
