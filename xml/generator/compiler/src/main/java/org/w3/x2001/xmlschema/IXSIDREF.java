package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSIDREF<T extends BindingType> extends IXSNCName<T>
{
	public IXSIDREF(IXSIDREF<T> binding)
	{
		super(binding);
	}

	public IXSIDREF(String value)
	{
		super(value);
	}

	protected IXSIDREF()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(String text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(value);
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSIDREF clone()
	{
		return new IXSIDREF(this)
		{
			protected IXSIDREF inherits()
			{
				return this;
			}
		};
	}
}
