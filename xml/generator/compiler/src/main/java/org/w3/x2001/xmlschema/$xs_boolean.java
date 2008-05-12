package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_boolean<T extends BindingType> extends $xs_anySimpleType<T>
{
	private static final Map<Boolean,String[]> valueMap = new HashMap<Boolean,String[]>();

	public static final Boolean parseBoolean(String s)
	{
		if(s == null)
			return false;

		if(s.length() == 1)
			return "1".equals(s);

		return Boolean.parseBoolean(s);
	}

	static
	{
		valueMap.put(true, new String[]{"true", "1"});
		valueMap.put(false, new String[]{"false", "0"});
	}

	public $xs_boolean($xs_boolean<T> binding)
	{
		super(binding);
	}

	public $xs_boolean(Boolean value)
	{
		super(value);
	}

	protected $xs_boolean()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Boolean text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Boolean.valueOf("true".equals(value) || "1".equals(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		if(_$$getPattern() == null)
			return String.valueOf(super.getText());

		for(String pattern : _$$getPattern())
		{
			String[] ret = valueMap.get(super.getText());
			for(int i = 0; i < ret.length; i++)
			{
				if(ret[i].matches(pattern))
					return ret[i];
			}
		}

		throw new MarshalException("No valid return type. Schema error!!!");
	}

	public $xs_boolean clone()
	{
		return new $xs_boolean(this)
		{
			protected $xs_boolean inherits()
			{
				return this;
			}
		};
	}
}