package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_short<T extends BindingType> extends $xs_int<T>
{
	public $xs_short($xs_short<T> binding)
	{
		super(binding);
	}

	public $xs_short(Short value)
	{
		super(value);
	}

	protected $xs_short(Number value)
	{
		super(value);
	}

	protected $xs_short()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Short text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Short.parseShort(String.valueOf(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_short clone()
	{
		return new $xs_short(this)
		{
			protected $xs_short inherits()
			{
				return this;
			}
		};
	}
}
