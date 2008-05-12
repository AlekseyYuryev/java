package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.NotationType;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_NOTATION<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_NOTATION($xs_NOTATION<T> binding)
	{
		super(binding);
	}

	public $xs_NOTATION(NotationType value)
	{
		super(value);
	}

	protected $xs_NOTATION()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(NotationType text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(NotationType.parseNotation(value));
		if(super.getText() == null)
			throw new ParseException("Notation \"" + value + "\" is not registered. The code that instantiates the Notation binding for \"" + value + "\" must be run before it is possible for the Binding engine to have to know about it.");
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_NOTATION clone()
	{
		return new $xs_NOTATION(this)
		{
			protected $xs_NOTATION inherits()
			{
				return this;
			}
		};
	}
}
