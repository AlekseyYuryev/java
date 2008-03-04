package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.commons.xml.binding.Year;
import org.w3c.dom.Element;

public abstract class IXSGYear<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSGYear(IXSGYear<T> binding)
	{
		super(binding);
	}

	public IXSGYear(Year value)
	{
		super(value);
	}

	protected IXSGYear()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Year text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Year.parseYear(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSGYear clone()
	{
		return new IXSGYear(this)
		{
			protected IXSGYear inherits()
			{
				return this;
			}
		};
	}
}
