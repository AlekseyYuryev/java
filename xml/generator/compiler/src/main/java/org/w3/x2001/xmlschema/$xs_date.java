package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Date;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_date<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_date($xs_date<T> binding)
	{
		super(binding);
	}

	public $xs_date(Date value)
	{
		super(value);
	}

	protected $xs_date()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Date text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Date.parseDate(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_date clone()
	{
		return new $xs_date(this)
		{
			protected $xs_date inherits()
			{
				return this;
			}
		};
	}
}
