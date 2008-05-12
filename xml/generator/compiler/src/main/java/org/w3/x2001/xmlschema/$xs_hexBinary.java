package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.HexBinary;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_hexBinary<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_hexBinary($xs_hexBinary<T> binding)
	{
		super(binding);
	}

	public $xs_hexBinary(HexBinary value)
	{
		super(value);
	}

	protected $xs_hexBinary()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(HexBinary text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(HexBinary.parseHexBinary(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_hexBinary clone()
	{
		return new $xs_hexBinary(this)
		{
			protected $xs_hexBinary inherits()
			{
				return this;
			}
		};
	}
}
