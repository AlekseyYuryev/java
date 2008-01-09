package org.w3.x2001.xmlschema;

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSQName;
import org.w3c.dom.Element;

public abstract class IXSQName<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSQName(IXSQName<T> binding)
	{
		super(binding);
	}

	public IXSQName(QName value)
	{
		super();
	}

	protected IXSQName()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(QName text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		final QName temp = stringToQName(value);
		super.setTEXT(temp);
		if(element != null)
			super.setTEXT(new javax.xml.namespace.QName(element.getOwnerDocument().getDocumentElement().lookupNamespaceURI(temp.getPrefix()), temp.getLocalPart()));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		final QName value = (QName)super.getTEXT();
		if(parent != null && value.getNamespaceURI() != null)
			return _getPrefix(parent, value) + ":" + value.getLocalPart();
		else
			return value.getLocalPart();
	}

	public IXSQName clone()
	{
		return new IXSQName(this)
		{
			protected IXSQName inherits()
			{
				return this;
			}
		};
	}
}
