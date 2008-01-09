package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.lang.DateTime;
import org.w3c.dom.Element;

/**
 * This class represents the Java binding of the dateTime instance of time.
 *
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#dateTime">Definition</a>
 */
public abstract class IXSDateTime<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSDateTime(IXSDateTime<T> binding)
	{
		super(binding);
	}

	public IXSDateTime(DateTime value)
	{
		super(value);
	}

	/**
	 * Allocates a <code>Date</code> object and initializes it so that it
	 * represents the time at which it was allocated. Milliseconds are
	 * <b>NOT</b> significant figures and are not represented.
	 *
	 * @see java.lang.System#currentTimeMillis()
	 */
	protected IXSDateTime()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(DateTime text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(DateTime.parseDateTime(value));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSDateTime clone()
	{
		return new IXSDateTime(this)
		{
			protected IXSDateTime inherits()
			{
				return this;
			}
		};
	}
}
