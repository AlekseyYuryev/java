package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_ENTITY<T extends BindingType> extends $xs_NCName<T>
{
	public $xs_ENTITY($xs_ENTITY<T> binding)
	{
		super(binding);
	}

	public $xs_ENTITY(String value)
	{
		super(value);
	}

	protected $xs_ENTITY()
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

	public $xs_ENTITY clone()
	{
		return new $xs_ENTITY(this)
		{
			protected $xs_ENTITY inherits()
			{
				return this;
			}
		};
	}
}
