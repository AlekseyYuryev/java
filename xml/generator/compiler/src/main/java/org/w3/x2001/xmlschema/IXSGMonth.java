package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.commons.xml.binding.Month;
import org.w3c.dom.Element;

public abstract class IXSGMonth<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSGMonth(IXSGMonth<T> binding)
	{
		super(binding);
	}

	public IXSGMonth(Month value)
	{
		super(value);
	}

	protected IXSGMonth()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Month text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Month.parseMonth(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSGMonth clone()
	{
		return new IXSGMonth(this)
		{
			protected IXSGMonth inherits()
			{
				return this;
			}
		};
	}
}
