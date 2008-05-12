package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.YearMonth;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_gYearMonth<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_gYearMonth($xs_gYearMonth<T> binding)
	{
		super(binding);
	}

	public $xs_gYearMonth(YearMonth value)
	{
		super(value);
	}

	protected $xs_gYearMonth()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(YearMonth text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(YearMonth.parseYearMonth(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_gYearMonth clone()
	{
		return new $xs_gYearMonth(this)
		{
			protected $xs_gYearMonth inherits()
			{
				return this;
			}
		};
	}
}
