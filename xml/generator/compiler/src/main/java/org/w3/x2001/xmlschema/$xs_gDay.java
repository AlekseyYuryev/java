package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Day;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_gDay<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_gDay($xs_gDay<T> binding)
	{
		super(binding);
	}

	public $xs_gDay(Day value)
	{
		super(value);
	}

	protected $xs_gDay()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Day text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Day.parseDay(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_gDay clone()
	{
		return new $xs_gDay(this)
		{
			protected $xs_gDay inherits()
			{
				return this;
			}
		};
	}
}
