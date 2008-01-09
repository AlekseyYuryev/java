package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Day;
import org.w3.x2001.xmlschema.IXSGDay;
import org.w3c.dom.Element;

public abstract class IXSGDay<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSGDay(IXSGDay<T> binding)
	{
		super(binding);
	}

	public IXSGDay(Day value)
	{
		super(value);
	}

	protected IXSGDay()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Day text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Day.parseDay(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSGDay clone()
	{
		return new IXSGDay(this)
		{
			protected IXSGDay inherits()
			{
				return this;
			}
		};
	}
}
