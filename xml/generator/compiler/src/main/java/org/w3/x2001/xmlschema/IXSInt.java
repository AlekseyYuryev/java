package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSInt<T extends BindingType> extends IXSLong<T>
{
	public IXSInt(IXSInt<T> binding)
	{
		super(binding);
	}

	public IXSInt(Integer value)
	{
		super(value);
	}

	protected IXSInt(Number value)
	{
		super(value);
	}

	protected IXSInt()
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
		super.setTEXT(Integer.parseInt(String.valueOf(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSInt clone()
	{
		return new IXSInt(this)
		{
			protected IXSInt inherits()
			{
				return this;
			}
		};
	}
}
