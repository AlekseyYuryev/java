package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSNormalizedString;
import org.w3c.dom.Element;

public abstract class IXSNormalizedString<T extends BindingType> extends IXSString<T>
{
	public IXSNormalizedString(IXSNormalizedString<T> binding)
	{
		super(binding);
	}

	public IXSNormalizedString(CharSequence value)
	{
		super(value);
	}

	protected IXSNormalizedString()
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

	protected void _decode(CharSequence element, CharSequence value) throws ParseException
	{
		super.setTEXT(value);
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSNormalizedString clone()
	{
		return new IXSNormalizedString(this)
		{
			protected IXSNormalizedString inherits()
			{
				return this;
			}
		};
	}
}
