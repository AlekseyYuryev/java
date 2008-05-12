package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Decimal;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_decimal<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_decimal($xs_decimal<T> binding)
	{
		super(binding);
	}

	public $xs_decimal(Decimal value)
	{
		super(value);
	}

	protected $xs_decimal(Number value)
	{
		super(value);
	}

	protected $xs_decimal()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Decimal text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Decimal.parseDecimal(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_decimal clone()
	{
		return new $xs_decimal(this)
		{
			protected $xs_decimal inherits()
			{
				return this;
			}
		};
	}
}