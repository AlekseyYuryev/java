package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Date;
import org.w3c.dom.Element;

public abstract class IXSDate<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSDate(IXSDate<T> binding)
	{
		super(binding);
	}

	public IXSDate(Date value)
	{
		super(value);
	}

	protected IXSDate()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Date text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Date.parseDate(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSDate clone()
	{
		return new IXSDate(this)
		{
			protected IXSDate inherits()
			{
				return this;
			}
		};
	}
}
