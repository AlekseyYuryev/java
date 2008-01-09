package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.NotationType;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3.x2001.xmlschema.IXSNOTATION;
import org.w3c.dom.Element;

public abstract class IXSNOTATION<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSNOTATION(IXSNOTATION<T> binding)
	{
		super(binding);
	}

	public IXSNOTATION(NotationType value)
	{
		super(value);
	}

	protected IXSNOTATION()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(NotationType text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(NotationType.parseNotation(value));
		if(super.getTEXT() == null)
			throw new ParseException("Notation \"" + value + "\" is not registered. The code that instantiates the Notation binding for \"" + value + "\" must be run before it is possible for the Binding engine to have to know about it.");
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSNOTATION clone()
	{
		return new IXSNOTATION(this)
		{
			protected IXSNOTATION inherits()
			{
				return this;
			}
		};
	}
}
