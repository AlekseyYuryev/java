package org.safris.xml.generator.lexer.phase.model.element;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.Referenceable;
import org.safris.xml.generator.lexer.phase.model.AliasModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.ReferableModel;
import org.safris.xml.generator.lexer.phase.model.RestrictableModel;
import org.safris.xml.generator.lexer.phase.model.element.SimpleTypeModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Use;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class AttributeModel extends SimpleTypeModel<SimpleTypeModel> implements ReferableModel<AttributeModel>, RestrictableModel<AttributeModel>
{
	private QName _default = null;
	private QName fixed = null;
	private Form form = null;
	private Use use = Use.OPTIONAL;
	private Form attributeFormDefault = null;
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
				setRef(AttributeModel.Reference.parseAttribute(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
			else if("type".equals(attribute.getLocalName()))
				setSuperType(SimpleTypeModel.Reference.parseSimpleType(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
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

	public final BindingQName getName()
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

	public final void setAttributeFormDefault(Form attributeFormDefault)
	{
		this.attributeFormDefault = attributeFormDefault;
	}

	public final Form getAttributeFormDefault()
	{
		return attributeFormDefault;
	}

	public boolean equals(Object obj)
	{
		boolean equals = super.equals(obj);
		if(!equals)
			return false;

		AttributeModel attributeModel = (AttributeModel)obj;
		return (getRef() == null && attributeModel.getRef() == null) || (getRef() != null && getRef().equals(attributeModel.getRef()));
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
		private static final Map<BindingQName,Reference> all = new HashMap<BindingQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseAttribute(BindingQName name)
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
