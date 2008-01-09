package org.w3.x2001.xmlschema;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.compiler.runtime.ValidationException;
import org.w3.x2001.xmlschema.IXSNMTOKENS;
import org.w3c.dom.Element;

public abstract class IXSNMTOKENS<T extends BindingType> extends IXSAnySimpleType<T>
{
	public IXSNMTOKENS(IXSNMTOKENS<T> binding)
	{
		super(binding);
	}

	public IXSNMTOKENS(List<String> value)
	{
		super(value);
	}

	protected IXSNMTOKENS()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(List<String> text)
	{
		super.setTEXT(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		if(value == null || value.length() == 0)
			return;

		super.setTEXT(new ArrayList<String>());
		StringTokenizer tokenizer = new StringTokenizer(String.valueOf(value));
		while(tokenizer.hasMoreTokens())
			((List<String>)super.getTEXT()).add(tokenizer.nextToken());
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null || ((List)super.getTEXT()).size() == 0)
			return null;

		String value = "";
		for(String temp : (List<String>)super.getTEXT())
			value += " " + temp;

		return value.substring(1);
	}

	public IXSNMTOKENS clone()
	{
		return new IXSNMTOKENS(this)
		{
			protected IXSNMTOKENS inherits()
			{
				return this;
			}
		};
	}
}
