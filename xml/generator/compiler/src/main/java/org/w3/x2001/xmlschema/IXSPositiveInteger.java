package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSPositiveInteger<T extends BindingType> extends IXSInteger<T>
{
	public IXSPositiveInteger(IXSPositiveInteger<T> binding)
	{
		super(binding);
	}

	public IXSPositiveInteger(Integer value)
	{
		super(value);
	}

	protected IXSPositiveInteger()
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

	public IXSPositiveInteger clone()
	{
		return new IXSPositiveInteger(this)
		{
			protected IXSPositiveInteger inherits()
			{
				return this;
			}
		};
	}
}
