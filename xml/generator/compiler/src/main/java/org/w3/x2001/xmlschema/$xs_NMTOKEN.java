package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_NMTOKEN<T extends BindingType> extends $xs_token<T>
{
	public $xs_NMTOKEN($xs_NMTOKEN<T> binding)
	{
		super(binding);
	}

	public $xs_NMTOKEN(String value)
	{
		super(value);
	}

	protected $xs_NMTOKEN()
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

	public $xs_NMTOKEN clone()
	{
		return new $xs_NMTOKEN(this)
		{
			protected $xs_NMTOKEN inherits()
			{
				return this;
			}
		};
	}
}
