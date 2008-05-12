package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Duration;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_duration<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_duration($xs_duration<T> binding)
	{
		super(binding);
	}

	public $xs_duration(Duration value)
	{
		super();
	}

	protected $xs_duration()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Duration text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Duration.parseDuration(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_duration clone()
	{
		return new $xs_duration(this)
		{
			protected $xs_duration inherits()
			{
				return this;
			}
		};
	}
}
