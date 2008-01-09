package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSFloat<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSFloat(IXSFloat<T> binding)
	{
		super(binding);
	}

	public IXSFloat(Float value)
	{
		super(value);
	}

	protected IXSFloat()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Float text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Float.parseFloat(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSFloat clone()
	{
		return new IXSFloat(this)
		{
			protected IXSFloat inherits()
			{
				return this;
			}
		};
	}
}
