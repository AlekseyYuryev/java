package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSInteger;
import org.w3c.dom.Element;

public abstract class IXSInteger<T extends BindingType> extends IXSDecimal<T>
{
	public IXSInteger(IXSInteger<T> binding)
	{
		super(binding);
	}

	public IXSInteger(Integer value)
	{
		super(value);
	}

	protected IXSInteger(Number value)
	{
		super(value);
	}

	protected IXSInteger()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Integer text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Integer.parseInt(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSInteger clone()
	{
		return new IXSInteger(this)
		{
			protected IXSInteger inherits()
			{
				return this;
			}
		};
	}
}
