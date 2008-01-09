package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Duration;
import org.w3.x2001.xmlschema.IXSDuration;
import org.w3c.dom.Element;

public abstract class IXSDuration<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSDuration(IXSDuration<T> binding)
	{
		super(binding);
	}

	public IXSDuration(Duration value)
	{
		super();
	}

	protected IXSDuration()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Duration text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Duration.parseDuration(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSDuration clone()
	{
		return new IXSDuration(this)
		{
			protected IXSDuration inherits()
			{
				return this;
			}
		};
	}
}
