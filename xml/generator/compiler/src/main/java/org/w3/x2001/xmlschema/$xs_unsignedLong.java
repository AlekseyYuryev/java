package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_unsignedLong<T extends BindingType> extends $xs_nonNegativeInteger<T>
{
	public $xs_unsignedLong($xs_unsignedLong<T> binding)
	{
		super(binding);
	}

	public $xs_unsignedLong(Long value)
	{
		super(value);
	}

	protected $xs_unsignedLong(Number value)
	{
		super(value);
	}

	protected $xs_unsignedLong()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Long text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Long.parseLong(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_unsignedLong clone()
	{
		return new $xs_unsignedLong(this)
		{
			protected $xs_unsignedLong inherits()
			{
				return this;
			}
		};
	}
}
