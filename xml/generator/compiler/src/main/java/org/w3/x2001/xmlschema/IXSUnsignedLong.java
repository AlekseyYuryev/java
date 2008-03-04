package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSUnsignedLong<T extends BindingType> extends IXSNonNegativeInteger<T>
{
	public IXSUnsignedLong(IXSUnsignedLong<T> binding)
	{
		super(binding);
	}

	public IXSUnsignedLong(Long value)
	{
		super(value);
	}

	protected IXSUnsignedLong(Number value)
	{
		super(value);
	}

	protected IXSUnsignedLong()
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
		super.setTEXT(Long.parseLong(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSUnsignedLong clone()
	{
		return new IXSUnsignedLong(this)
		{
			protected IXSUnsignedLong inherits()
			{
				return this;
			}
		};
	}
}
