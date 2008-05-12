package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_IDREF<T extends BindingType> extends $xs_NCName<T>
{
	public $xs_IDREF($xs_IDREF<T> binding)
	{
		super(binding);
	}

	public $xs_IDREF(String value)
	{
		super(value);
	}

	protected $xs_IDREF()
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

	public $xs_IDREF clone()
	{
		return new $xs_IDREF(this)
		{
			protected $xs_IDREF inherits()
			{
				return this;
			}
		};
	}
}
