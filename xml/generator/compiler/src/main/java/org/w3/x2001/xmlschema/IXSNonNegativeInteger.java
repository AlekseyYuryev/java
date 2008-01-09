package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSNonNegativeInteger;
import org.w3c.dom.Element;

public abstract class IXSNonNegativeInteger<T extends BindingType> extends IXSInteger<T>
{
	public IXSNonNegativeInteger(IXSNonNegativeInteger<T> binding)
	{
		super(binding);
	}

	public IXSNonNegativeInteger(Integer value)
	{
		super(value);
	}

	protected IXSNonNegativeInteger(Number value)
	{
		super(value);
	}

	protected IXSNonNegativeInteger()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Integer text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Integer.parseInt(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSNonNegativeInteger clone()
	{
		return new IXSNonNegativeInteger(this)
		{
			protected IXSNonNegativeInteger inherits()
			{
				return this;
			}
		};
	}
}
