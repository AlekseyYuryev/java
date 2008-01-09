package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Decimal;
import org.w3.x2001.xmlschema.IXSDecimal;
import org.w3c.dom.Element;

public abstract class IXSDecimal<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSDecimal(IXSDecimal<T> binding)
	{
		super(binding);
	}

	public IXSDecimal(Decimal value)
	{
		super(value);
	}

	protected IXSDecimal(Number value)
	{
		super(value);
	}

	protected IXSDecimal()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Decimal text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Decimal.parseDecimal(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSDecimal clone()
	{
		return new IXSDecimal(this)
		{
			protected IXSDecimal inherits()
			{
				return this;
			}
		};
	}
}
