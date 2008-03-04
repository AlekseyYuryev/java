package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSDouble<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSDouble(IXSDouble<T> binding)
	{
		super(binding);
	}

	public IXSDouble(Double value)
	{
		super(value);
	}

	protected IXSDouble()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Double text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Double.parseDouble(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSDouble clone()
	{
		return new IXSDouble(this)
		{
			protected IXSDouble inherits()
			{
				return this;
			}
		};
	}
}
