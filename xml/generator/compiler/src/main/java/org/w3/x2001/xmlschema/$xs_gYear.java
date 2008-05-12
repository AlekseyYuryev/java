package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Year;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_gYear<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_gYear($xs_gYear<T> binding)
	{
		super(binding);
	}

	public $xs_gYear(Year value)
	{
		super(value);
	}

	protected $xs_gYear()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Year text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Year.parseYear(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_gYear clone()
	{
		return new $xs_gYear(this)
		{
			protected $xs_gYear inherits()
			{
				return this;
			}
		};
	}
}
