package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSNMTOKEN<T extends BindingType> extends IXSToken<T>
{
	public IXSNMTOKEN(IXSNMTOKEN<T> binding)
	{
		super(binding);
	}

	public IXSNMTOKEN(String value)
	{
		super(value);
	}

	protected IXSNMTOKEN()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(String text)
	{
		super.setTEXT((CharSequence)text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT((CharSequence)value);
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSNMTOKEN clone()
	{
		return new IXSNMTOKEN(this)
		{
			protected IXSNMTOKEN inherits()
			{
				return this;
			}
		};
	}
}
