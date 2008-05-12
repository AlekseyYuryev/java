package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Time;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_time<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_time($xs_time<T> binding)
	{
		super(binding);
	}

	public $xs_time(Time value)
	{
		super(value);
	}

	protected $xs_time()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Time text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Time.parseTime(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_time clone()
	{
		return new $xs_time(this)
		{
			protected $xs_time inherits()
			{
				return this;
			}
		};
	}
}
