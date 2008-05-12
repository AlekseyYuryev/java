package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_integer<T extends BindingType> extends $xs_decimal<T>
{
	public $xs_integer($xs_integer<T> binding)
	{
		super(binding);
	}

	public $xs_integer(Integer value)
	{
		super(value);
	}

	protected $xs_integer(Number value)
	{
		super(value);
	}

	protected $xs_integer()
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
		super.setText(Integer.parseInt(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_integer clone()
	{
		return new $xs_integer(this)
		{
			protected $xs_integer inherits()
			{
				return this;
			}
		};
	}
}
