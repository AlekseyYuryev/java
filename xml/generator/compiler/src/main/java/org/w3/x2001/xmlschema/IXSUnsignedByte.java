package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSUnsignedByte;
import org.w3c.dom.Element;

public abstract class IXSUnsignedByte<T extends BindingType> extends IXSUnsignedShort<T>
{
	public IXSUnsignedByte(IXSUnsignedByte<T> binding)
	{
		super(binding);
	}

	public IXSUnsignedByte(Byte value)
	{
		super(value);
	}

	protected IXSUnsignedByte()
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
		super.setTEXT(Byte.parseByte(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSUnsignedByte clone()
	{
		return new IXSUnsignedByte(this)
		{
			protected IXSUnsignedByte inherits()
			{
				return this;
			}
		};
	}
}
