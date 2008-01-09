package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSShort<T extends BindingType> extends IXSInt<T>
{
	public IXSShort(IXSShort<T> binding)
	{
		super(binding);
	}

	public IXSShort(Short value)
	{
		super(value);
	}

	protected IXSShort(Number value)
	{
		super(value);
	}

	protected IXSShort()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Short text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Short.parseShort(String.valueOf(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSShort clone()
	{
		return new IXSShort(this)
		{
			protected IXSShort inherits()
			{
				return this;
			}
		};
	}
}
