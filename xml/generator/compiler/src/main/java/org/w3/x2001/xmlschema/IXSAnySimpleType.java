package org.w3.x2001.xmlschema;

import javax.xml.namespace.QName;
import org.safris.commons.xml.validator.ValidationException;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public abstract class IXSAnySimpleType<T extends BindingType> extends Binding<T>
{
	private Object text = null;

	private IXSAnySimpleType(IXSAnySimpleType<T> binding)
	{
		super(binding);
		this.text = binding.text;
	}

	public IXSAnySimpleType(Object text)
	{
		super();
		this.text = text;
	}

	protected IXSAnySimpleType(String text)
	{
		super();
		this.text = text;
	}

	protected IXSAnySimpleType()
	{
		super();
	}

	protected void setTEXT(Object text)
	{
		this.text = text;
	}

	protected Object getTEXT()
	{
		return text;
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		this.text = value;
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	private transient Element parent = null;

	protected Element marshal(Element parent, QName name, QName typeName) throws MarshalException
	{
		this.parent = parent;
		final Element element = super.marshal(parent, name, typeName);
		if(text != null)
		{
			final Document document = parent.getOwnerDocument();
			element.appendChild(document.createTextNode(String.valueOf(_encode(parent))));
		}

		return element;
	}

	protected Attr marshalAttr(String name, Element parent) throws MarshalException
	{
		this.parent = parent;
		final Attr attr = parent.getOwnerDocument().createAttribute(name);
		attr.setNodeValue(String.valueOf(_encode(parent)));
		return attr;
	}

	protected void parse(Element element) throws ParseException, ValidationException
	{
		String value = "";
		final NodeList nodeList = element.getChildNodes();
		for(int i = 0; i < nodeList.getLength(); i++)
			if(nodeList.item(i).getNodeValue() != null)
				value += nodeList.item(i).getNodeValue().trim();

		if(value.length() == 0)
			return;

		_decode(element, value);
	}

	protected QName _getName()
	{
		return _getName(inherits());
	}

	protected QName _getTypeName()
	{
		return null;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof IXSAnySimpleType))
			return false;

		final IXSAnySimpleType xsType = (IXSAnySimpleType)obj;
		try
		{
			final String encoded = _encode(parent);
			final String objEncoded = xsType._encode(parent);
			return encoded == null ? objEncoded == null : encoded.equals(objEncoded);
		}
		catch(MarshalException e)
		{
			return false;
		}
	}

	public IXSAnySimpleType clone()
	{
		return new IXSAnySimpleType(this)
		{
			protected IXSAnySimpleType inherits()
			{
				return this;
			}
		};
	}

	public int hashCode()
	{
		final String value;
		try
		{
			value = _encode(parent);
		}
		catch(MarshalException e)
		{
			return super.hashCode();
		}

		if(value == null)
			return super.hashCode();

		return value.hashCode();
	}

	public String toString()
	{
		try
		{
			return String.valueOf(_encode(parent));
		}
		catch(MarshalException e)
		{
			return super.toString();
		}
	}
}
