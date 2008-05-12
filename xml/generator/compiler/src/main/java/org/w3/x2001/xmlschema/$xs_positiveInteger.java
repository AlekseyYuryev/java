package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_positiveInteger<T extends BindingType> extends $xs_integer<T>
{
	public $xs_positiveInteger($xs_positiveInteger<T> binding)
	{
		super(binding);
	}

	public $xs_positiveInteger(Integer value)
	{
		super(value);
	}

	protected $xs_positiveInteger()
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

	public $xs_positiveInteger clone()
	{
		return new $xs_positiveInteger(this)
		{
			protected $xs_positiveInteger inherits()
			{
				return this;
			}
		};
	}
}
