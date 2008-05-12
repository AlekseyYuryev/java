package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_long<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_long($xs_long<T> binding)
	{
		super(binding);
	}

	public $xs_long(Long value)
	{
		super(value);
	}

	protected $xs_long(Number value)
	{
		super(value);
	}

	protected $xs_long()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Long text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Long.parseLong(String.valueOf(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_long clone()
	{
		return new $xs_long(this)
		{
			protected $xs_long inherits()
			{
				return this;
			}
		};
	}
}
