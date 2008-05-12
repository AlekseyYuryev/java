package org.w3.x2001.xmlschema;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_ENTITIES<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_ENTITIES($xs_ENTITIES<T> binding)
	{
		super(binding);
	}

	public $xs_ENTITIES(List<String> value)
	{
		super(value);
	}

	protected $xs_ENTITIES()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(List<String> text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		if(value == null || value.length() == 0)
			return;

		super.setText(new ArrayList<String>());
		StringTokenizer tokenizer = new StringTokenizer(String.valueOf(value));
		while(tokenizer.hasMoreTokens())
			((List<String>)super.getText()).add(tokenizer.nextToken());
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null || ((List)super.getText()).size() == 0)
			return null;

		String value = "";
		for(String temp : (List<String>)super.getText())
			value += " " + temp;

		return value.substring(1);
	}

	public $xs_ENTITIES clone()
	{
		return new $xs_ENTITIES(this)
		{
			protected $xs_ENTITIES inherits()
			{
				return this;
			}
		};
	}
}