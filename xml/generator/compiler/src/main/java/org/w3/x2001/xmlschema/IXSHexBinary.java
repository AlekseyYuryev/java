package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.HexBinary;
import org.w3.x2001.xmlschema.IXSHexBinary;
import org.w3c.dom.Element;

public abstract class IXSHexBinary<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSHexBinary(IXSHexBinary<T> binding)
	{
		super(binding);
	}

	public IXSHexBinary(HexBinary value)
	{
		super(value);
	}

	protected IXSHexBinary()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(HexBinary text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(HexBinary.parseHexBinary(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSHexBinary clone()
	{
		return new IXSHexBinary(this)
		{
			protected IXSHexBinary inherits()
			{
				return this;
			}
		};
	}
}
