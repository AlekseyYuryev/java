package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class IXSBoolean<T extends BindingType> extends IXSAnySimpleType<T>
{
	private static final Map<Boolean,String[]> valueMap = new HashMap<Boolean,String[]>();

	public static final Boolean parseBoolean(String s)
	{
		if(s.length() == 1)
			return "1".equals(s);

		return Boolean.parseBoolean(s);
	}

	static
	{
		valueMap.put(true, new String[]{"true", "1"});
		valueMap.put(false, new String[]{"false", "0"});
	}

	public IXSBoolean(IXSBoolean<T> binding)
	{
		super(binding);
	}

	public IXSBoolean(Boolean value)
	{
		super(value);
	}

	protected IXSBoolean()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(Boolean text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		super.setTEXT(Boolean.valueOf("true".equals(value) || "1".equals(value)));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		if(_getPattern() == null)
			return String.valueOf(super.getTEXT());

		for(String pattern : _getPattern())
		{
			String[] ret = valueMap.get(super.getTEXT());
			for(int i = 0; i < ret.length; i++)
			{
				if(ret[i].matches(pattern))
					return ret[i];
			}
		}

		throw new MarshalException("No valid return type. Schema error!!!");
	}

	public IXSBoolean clone()
	{
		return new IXSBoolean(this)
		{
			protected IXSBoolean inherits()
			{
				return this;
			}
		};
	}
}
