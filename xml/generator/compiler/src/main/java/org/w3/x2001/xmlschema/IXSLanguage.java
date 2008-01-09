package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.Language;
import org.w3.x2001.xmlschema.IXSLanguage;
import org.w3c.dom.Element;

public abstract class IXSLanguage<T extends BindingType> extends IXSToken<T>
{
	public IXSLanguage(IXSLanguage<T> binding)
	{
		super(binding);
	}

	public IXSLanguage(Language value)
	{
		super(value);
	}

	protected IXSLanguage()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Language text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Language.parseLanguage(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSLanguage clone()
	{
		return new IXSLanguage(this)
		{
			protected IXSLanguage inherits()
			{
				return this;
			}
		};
	}
}
