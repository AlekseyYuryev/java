package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.MonthDay;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_gMonthDay<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_gMonthDay($xs_gMonthDay<T> binding)
	{
		super(binding);
	}

	public $xs_gMonthDay(MonthDay value)
	{
		super(value);
	}

	protected $xs_gMonthDay()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(MonthDay text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(MonthDay.parseMonthDay(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_gMonthDay clone()
	{
		return new $xs_gMonthDay(this)
		{
			protected $xs_gMonthDay inherits()
			{
				return this;
			}
		};
	}
}