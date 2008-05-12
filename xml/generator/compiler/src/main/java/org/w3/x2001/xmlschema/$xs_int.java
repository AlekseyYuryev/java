package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_int<T extends BindingType> extends $xs_long<T>
{
	public $xs_int($xs_int<T> binding)
	{
		super(binding);
	}

	public $xs_int(Integer value)
	{
		super(value);
	}

	protected $xs_int(Number value)
	{
		super(value);
	}

	protected $xs_int()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Integer text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Integer.parseInt(String.valueOf(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_int clone()
	{
		return new $xs_int(this)
		{
			protected $xs_int inherits()
			{
				return this;
			}
		};
	}
}
