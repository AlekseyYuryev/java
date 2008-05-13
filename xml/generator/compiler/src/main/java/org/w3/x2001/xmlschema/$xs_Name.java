package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_Name<T extends BindingType> extends $xs_token<T>
{
	public $xs_Name($xs_Name<T> binding)
	{
		super(binding);
	}

	public $xs_Name(String value)
	{
		super(value);
	}

	protected $xs_Name()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(String text)
	{
		super.setText((CharSequence)text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText((CharSequence)value);
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_Name clone()
	{
		return new $xs_Name(this)
		{
			protected $xs_Name inherits()
			{
				return this;
			}
		};
	}
}
