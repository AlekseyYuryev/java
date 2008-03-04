package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.commons.xml.binding.MonthDay;
import org.w3.x2001.xmlschema.IXSMonthDay;
import org.w3c.dom.Element;

public abstract class IXSMonthDay<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSMonthDay(IXSMonthDay<T> binding)
	{
		super(binding);
	}

	public IXSMonthDay(MonthDay value)
	{
		super(value);
	}

	protected IXSMonthDay()
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

	public IXSMonthDay clone()
	{
		return new IXSMonthDay(this)
		{
			protected IXSMonthDay inherits()
			{
				return this;
			}
		};
	}
}
