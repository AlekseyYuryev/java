package org.safris.xml.generator.compiler.runtime;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;

public class ElementAudit<T>
{
	protected static final QName XSI_NIL = new QName("http://www.w3.org/2001/XMLSchema-instance", "nil", "xsi");

	private final T _default;
	private final QName name;
	private final QName typeName;
	private final boolean nillable;
	private final boolean qualified;
	private final int minOccurs;
	private final int maxOccurs;
	private T value = null;

	public ElementAudit(T _default, QName name, QName typeName, boolean qualified, boolean nillable, int minOccurs, int maxOccurs)
	{
		this._default = _default;
		this.name = name;
		this.typeName = typeName;
		this.qualified = qualified;
		this.nillable = nillable;
		this.minOccurs = minOccurs;
		this.maxOccurs = maxOccurs;
	}

	public boolean isQualified()
	{
		return qualified;
	}

	public T getDefault()
	{
		return _default;
	}

	public QName getName()
	{
		return name;
	}

	public QName getTypeName()
	{
		return typeName;
	}

	public boolean isNillable()
	{
		return nillable;
	}

	public int getMinOccurs()
	{
		return minOccurs;
	}

	public int getMaxOccurs()
	{
		return maxOccurs;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public T getValue()
	{
		return value;
	}

	public void marshal(Element parent) throws MarshalException
	{
		Object value = getValue();
		if(value == null)
		{
			if(getDefault() == null)
				return;

			if(getMinOccurs() == 0)
				return;

			value = getDefault();
		}

		if(value == null && getName() != null && isNillable())
		{
			Element element = parent.getOwnerDocument().createElementNS(getName().getNamespaceURI(), getName().getLocalPart());
			element.setAttributeNS(Binding.XML.getNamespaceURI(), XSI_NIL.getPrefix() + ":" + XSI_NIL.getLocalPart(), "true");
			if(isQualified())
				element.setPrefix(Binding._getPrefix(parent, getName()));

			parent.appendChild(element);
		}
		else if(value instanceof Collection)
		{
			for(Object object : (Collection)value)
			{
				Binding binding = (Binding)object;
				QName name = getName();
				if(name == null)
					name = binding._getName();

				Element element = binding.marshal(parent, name, getTypeName());
				if(!isQualified())
					element.setPrefix(null);

				parent.appendChild(element);
			}
		}
		else
		{
			Element element = ((Binding)value).marshal(parent, getName(), getTypeName());
			if(!isQualified())
				element.setPrefix(null);

			parent.appendChild(element);
		}
	}

	public boolean equals(Object obj)
	{
		if(obj == null)
		{
			if(value == null)
				return true;

			return false;
		}

		return obj.equals(value);
	}

	public int hashCode()
	{
		if(value == null)
			return 0;

		return value.hashCode();
	}

	public String toString()
	{
		if(value == null)
			return super.toString();

		return value.toString();
	}
}
