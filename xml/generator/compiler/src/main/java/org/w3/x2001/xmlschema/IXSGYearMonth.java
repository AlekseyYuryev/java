package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.YearMonth;
import org.w3.x2001.xmlschema.IXSGYearMonth;
import org.w3c.dom.Element;

public abstract class IXSGYearMonth<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSGYearMonth(IXSGYearMonth<T> binding)
	{
		super(binding);
	}

	public IXSGYearMonth(YearMonth value)
	{
		super(value);
	}

	protected IXSGYearMonth()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(YearMonth text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(YearMonth.parseYearMonth(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSGYearMonth clone()
	{
		return new IXSGYearMonth(this)
		{
			protected IXSGYearMonth inherits()
			{
				return this;
			}
		};
	}
}
