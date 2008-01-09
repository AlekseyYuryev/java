package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Base64Binary;
import org.w3c.dom.Element;

public abstract class IXSBase64Binary<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSBase64Binary(IXSBase64Binary<T> binding)
	{
		super(binding);
	}

	public IXSBase64Binary(Base64Binary value)
	{
		super(value);
	}

	protected IXSBase64Binary()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Base64Binary text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Base64Binary.parseBase64Binary(String.valueOf(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSBase64Binary clone()
	{
		return new IXSBase64Binary(this)
		{
			protected IXSBase64Binary inherits()
			{
				return this;
			}
		};
	}
}
