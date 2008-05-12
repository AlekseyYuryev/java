package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_unsignedShort<T extends BindingType> extends $xs_unsignedInt<T>
{
	public $xs_unsignedShort($xs_unsignedShort<T> binding)
	{
		super(binding);
	}

	public $xs_unsignedShort(Short value)
	{
		super(value);
	}

	protected $xs_unsignedShort(Number value)
	{
		super(value);
	}

	protected $xs_unsignedShort()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Short text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Short.parseShort(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_unsignedShort clone()
	{
		return new $xs_unsignedShort(this)
		{
			protected $xs_unsignedShort inherits()
			{
				return this;
			}
		};
	}
}
