package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_NCName<T extends BindingType> extends $xs_Name<T>
{
	public $xs_NCName($xs_NCName<T> binding)
	{
		super(binding);
	}

	public $xs_NCName(String value)
	{
		super(value);
	}

	protected $xs_NCName()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(String text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(value);
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_NCName clone()
	{
		return new $xs_NCName(this)
		{
			protected $xs_NCName inherits()
			{
				return this;
			}
		};
	}
}
