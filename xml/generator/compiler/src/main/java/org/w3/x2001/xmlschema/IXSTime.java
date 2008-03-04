package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.commons.xml.binding.Time;
import org.w3.x2001.xmlschema.IXSTime;
import org.w3c.dom.Element;

public abstract class IXSTime<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSTime(IXSTime<T> binding)
	{
		super(binding);
	}

	public IXSTime(Time value)
	{
		super(value);
	}

	protected IXSTime()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Time text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Time.parseTime(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSTime clone()
	{
		return new IXSTime(this)
		{
			protected IXSTime inherits()
			{
				return this;
			}
		};
	}
}
