package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSLong<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSLong(IXSLong<T> binding)
	{
		super(binding);
	}

	public IXSLong(Long value)
	{
		super(value);
	}

	protected IXSLong(Number value)
	{
		super(value);
	}

	protected IXSLong()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Long text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Long.parseLong(String.valueOf(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSLong clone()
	{
		return new IXSLong(this)
		{
			protected IXSLong inherits()
			{
				return this;
			}
		};
	}
}
