package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSAnyURI<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSAnyURI(IXSAnyURI<T> binding)
	{
		super(binding);
	}

	public IXSAnyURI(String value)
	{
		super(value);
	}

	protected IXSAnyURI()
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

	public IXSAnyURI clone()
	{
		return new IXSAnyURI(this)
		{
			protected IXSAnyURI inherits()
			{
				return this;
			}
		};
	}
}
