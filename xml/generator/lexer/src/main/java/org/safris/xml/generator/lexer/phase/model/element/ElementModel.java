package org.safris.xml.generator.lexer.phase.model.element;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.util.xml.BindingQName;
import org.safris.xml.generator.lexer.phase.Referenceable;
import org.safris.xml.generator.lexer.phase.model.AliasModel;
import org.safris.xml.generator.lexer.phase.model.Model;
import org.safris.xml.generator.lexer.phase.model.MultiplicableModel;
import org.safris.xml.generator.lexer.phase.model.ReferableModel;
import org.safris.xml.generator.lexer.phase.model.RestrictableModel;
import org.safris.xml.generator.lexer.schema.attribute.Form;
import org.safris.xml.generator.lexer.schema.attribute.Occurs;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ElementModel extends ComplexTypeModel<SimpleTypeModel> implements MultiplicableModel, ReferableModel<ElementModel>, RestrictableModel<ElementModel>
{
	private Boolean _abstract = false;
	private QName _default = null;
	private QName fixed = null;
	private Occurs maxOccurs = Occurs.parseOccurs("1");
	private Occurs minOccurs = Occurs.parseOccurs("1");
	private Boolean nillable = false;
	private ElementModel ref = null;
	private BindingQName substitutionGroup = null;
	private Form elementFormDefault = null;
	private AliasModel restrictionOwner = null;
	private ElementModel restriction = null;

	protected ElementModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if(attribute.getNodeValue() == null)
				continue;

			if("abstract".equals(attribute.getLocalName()))
				_abstract = Boolean.parseBoolean(attribute.getNodeValue());
			else if("default".equals(attribute.getLocalName()))
				_default = parseQNameValue(attribute.getNodeValue(), node);
			else if("fixed".equals(attribute.getLocalName()))
				fixed = parseQNameValue(attribute.getNodeValue(), node);
			else if("maxOccurs".equals(attribute.getLocalName()))
				maxOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("minOccurs".equals(attribute.getLocalName()))
				minOccurs = Occurs.parseOccurs(attribute.getNodeValue());
			else if("nillable".equals(attribute.getLocalName()))
				nillable = Boolean.parseBoolean(attribute.getNodeValue());
			else if("ref".equals(attribute.getLocalName()))
				setRef(ElementModel.Reference.parseElement(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
			else if("substitutionGroup".equals(attribute.getLocalName()))
				substitutionGroup = BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node));
			else if("type".equals(attribute.getLocalName()))
				setSuperType(ComplexTypeModel.Reference.parseComplexType(BindingQName.getInstance(parseQNameValue(attribute.getNodeValue(), node))));
		}
	}

	public final Boolean getAbstract()
	{
		return _abstract;
	}

	public final void setRestriction(ElementModel restriction)
	{
		this.restriction = restriction;
	}

	public final ElementModel getRestriction()
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

	public final ElementModel getRef()
	{
		return ref;
	}

	public final void setRef(ElementModel ref)
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

	public final void setElementFormDefault(Form elementFormDefault)
	{
		this.elementFormDefault = elementFormDefault;
	}

	public final Form getElementFormDefault()
	{
		return elementFormDefault;
	}

	public final QName getDefault()
	{
		return _default;
	}

	public final QName getFixed()
	{
		return fixed;
	}

	public final Occurs getMaxOccurs()
	{
		return maxOccurs;
	}

	public final Occurs getMinOccurs()
	{
		return minOccurs;
	}

	public final Boolean getNillable()
	{
		return nillable;
	}

	public final BindingQName getSubstitutionGroup()
	{
		return substitutionGroup;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof ElementModel))
			return false;

		return getRef() == null ? getName().equals(((ElementModel)obj).getName()) : (((ElementModel)obj).getRef() != null ? getRef().getName().equals(((ElementModel)obj).getRef().getName()) : false);
	}

	public int hashCode()
	{
		if(getRef() != null && getRef().getName() != null)
			return ("ref" + getRef().getName().toString()).hashCode();

		if(getName() != null)
			return ("elem" + getName().toString()).hashCode();

		return super.hashCode();
	}

	public String toString()
	{
		return BindingQName.XS.getNamespaceURI() + " " + getName();
	}

	public static class Reference extends ElementModel implements Referenceable
	{
		private static final Map<BindingQName,Reference> all = new HashMap<BindingQName,Reference>();

		protected Reference(Model parent)
		{
			super(null, parent);
		}

		public static Reference parseElement(BindingQName name)
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
