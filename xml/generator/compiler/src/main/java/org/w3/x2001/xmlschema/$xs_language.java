package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.Language;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_language<T extends BindingType> extends $xs_token<T>
{
	public $xs_language($xs_language<T> binding)
	{
		super(binding);
	}

	public $xs_language(Language value)
	{
		super(value);
	}

	protected $xs_language()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Language text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Language.parseLanguage(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_language clone()
	{
		return new $xs_language(this)
		{
			protected $xs_language inherits()
			{
				return this;
			}
		};
	}
}
