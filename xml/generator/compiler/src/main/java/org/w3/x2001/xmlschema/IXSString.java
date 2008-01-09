package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSString<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSString(IXSString<T> binding)
	{
		super(binding);
	}

	public IXSString(CharSequence value)
	{
		super(value);
	}

	protected IXSString()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(CharSequence text)
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

	public IXSString clone()
	{
		return new IXSString(this)
		{
			protected IXSString inherits()
			{
				return this;
			}
		};
	}
}
