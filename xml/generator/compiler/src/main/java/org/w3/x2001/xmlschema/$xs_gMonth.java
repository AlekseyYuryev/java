package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Month;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_gMonth<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_gMonth($xs_gMonth<T> binding)
	{
		super(binding);
	}

	public $xs_gMonth(Month value)
	{
		super(value);
	}

	protected $xs_gMonth()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Month text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Month.parseMonth(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_gMonth clone()
	{
		return new $xs_gMonth(this)
		{
			protected $xs_gMonth inherits()
			{
				return this;
			}
		};
	}
}
