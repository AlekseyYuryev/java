package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_byte<T extends BindingType> extends $xs_short<T>
{
	public $xs_byte($xs_byte<T> binding)
	{
		super(binding);
	}

	public $xs_byte(Byte value)
	{
		super(value);
	}

	protected $xs_byte()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Byte text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Byte.parseByte(String.valueOf(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_byte clone()
	{
		return new $xs_byte(this)
		{
			protected $xs_byte inherits()
			{
				return this;
			}
		};
	}
}
