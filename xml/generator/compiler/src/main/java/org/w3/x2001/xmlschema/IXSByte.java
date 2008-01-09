package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSByte<T extends BindingType> extends IXSShort<T>
{
	public IXSByte(IXSByte<T> binding)
	{
		super(binding);
	}

	public IXSByte(Byte value)
	{
		super(value);
	}

	protected IXSByte()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Byte text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Byte.parseByte(String.valueOf(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSByte clone()
	{
		return new IXSByte(this)
		{
			protected IXSByte inherits()
			{
				return this;
			}
		};
	}
}
