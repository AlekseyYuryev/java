package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSUnsignedShort<T extends BindingType> extends IXSUnsignedInt<T>
{
	public IXSUnsignedShort(IXSUnsignedShort<T> binding)
	{
		super(binding);
	}

	public IXSUnsignedShort(Short value)
	{
		super(value);
	}

	protected IXSUnsignedShort(Number value)
	{
		super(value);
	}

	protected IXSUnsignedShort()
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
		super.setTEXT(Short.parseShort(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSUnsignedShort clone()
	{
		return new IXSUnsignedShort(this)
		{
			protected IXSUnsignedShort inherits()
			{
				return this;
			}
		};
	}
}
