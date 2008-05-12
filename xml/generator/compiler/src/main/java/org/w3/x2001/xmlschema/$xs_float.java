package org.w3.x2001.xmlschema;

import org.safris.commons.lang.Numbers;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_float<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_float($xs_float<T> binding)
	{
		super(binding);
	}

	public $xs_float(Float value)
	{
		super(value);
	}

	protected $xs_float()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Float text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Float.parseFloat(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return Numbers.roundInsignificant(super.getText().toString());
	}

	public $xs_float clone()
	{
		return new $xs_float(this)
		{
			protected $xs_float inherits()
			{
				return this;
			}
		};
	}
}