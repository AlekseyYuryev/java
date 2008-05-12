package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_string<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_string($xs_string<T> binding)
	{
		super(binding);
	}

	public $xs_string(CharSequence value)
	{
		super(value);
	}

	protected $xs_string()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(CharSequence text)
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

	public $xs_string clone()
	{
		return new $xs_string(this)
		{
			protected $xs_string inherits()
			{
				return this;
			}
		};
	}
}
