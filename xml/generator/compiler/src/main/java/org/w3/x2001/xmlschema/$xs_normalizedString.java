package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_normalizedString<T extends BindingType> extends $xs_string<T>
{
	public $xs_normalizedString($xs_normalizedString<T> binding)
	{
		super(binding);
	}

	public $xs_normalizedString(CharSequence value)
	{
		super(value);
	}

	protected $xs_normalizedString()
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

	protected void _$$decode(CharSequence element, CharSequence value) throws ParseException
	{
		super.setText(value);
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_normalizedString clone()
	{
		return new $xs_normalizedString(this)
		{
			protected $xs_normalizedString inherits()
			{
				return this;
			}
		};
	}
}
