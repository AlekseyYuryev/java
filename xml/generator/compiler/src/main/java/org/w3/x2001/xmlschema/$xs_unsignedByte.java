package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_unsignedByte<T extends BindingType> extends $xs_unsignedShort<T>
{
	public $xs_unsignedByte($xs_unsignedByte<T> binding)
	{
		super(binding);
	}

	public $xs_unsignedByte(Byte value)
	{
		super(value);
	}

	protected $xs_unsignedByte()
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
		super.setText(Byte.parseByte(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_unsignedByte clone()
	{
		return new $xs_unsignedByte(this)
		{
			protected $xs_unsignedByte inherits()
			{
				return this;
			}
		};
	}
}
