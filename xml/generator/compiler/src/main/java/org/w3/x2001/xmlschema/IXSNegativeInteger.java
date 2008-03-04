package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSNegativeInteger<T extends BindingType> extends IXSPositiveInteger<T>
{
	public IXSNegativeInteger(IXSNegativeInteger<T> binding)
	{
		super(binding);
	}

	public IXSNegativeInteger(Integer value)
	{
		super(value);
	}

	protected IXSNegativeInteger()
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

	public IXSNegativeInteger clone()
	{
		return new IXSNegativeInteger(this)
		{
			protected IXSNegativeInteger inherits()
			{
				return this;
			}
		};
	}
}
