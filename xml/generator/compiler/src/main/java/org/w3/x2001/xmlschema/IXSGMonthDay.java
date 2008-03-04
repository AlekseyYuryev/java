package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.commons.xml.binding.MonthDay;
import org.w3c.dom.Element;

public abstract class IXSGMonthDay<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSGMonthDay(IXSGMonthDay<T> binding)
	{
		super(binding);
	}

	public IXSGMonthDay(MonthDay value)
	{
		super(value);
	}

	protected IXSGMonthDay()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(MonthDay text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(MonthDay.parseMonthDay(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSGMonthDay clone()
	{
		return new IXSGMonthDay(this)
		{
			protected IXSGMonthDay inherits()
			{
				return this;
			}
		};
	}
}
