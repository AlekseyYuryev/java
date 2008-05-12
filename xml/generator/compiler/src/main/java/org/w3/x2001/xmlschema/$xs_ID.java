package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_ID<T extends BindingType> extends $xs_NCName<T>
{
	protected static final Map<String,Map<Object,$xs_ID>> namespaceIds = new HashMap<String,Map<Object,$xs_ID>>();

	private static void persist(String namespace, Object value, $xs_ID id)
	{
		Map<Object,$xs_ID> ids;
		if((ids = namespaceIds.get(namespace)) == null)
			namespaceIds.put(namespace, ids = new HashMap<Object,$xs_ID>());

		ids.put(value, id);
	}

	private static void remove(String namespace, Object value)
	{
		Map<Object,$xs_ID> ids;
		if((ids = namespaceIds.get(namespace)) == null)
			return;

		ids.remove(value);
	}

	public static $xs_ID lookupId(String id)
	{
		final Map<Object,$xs_ID> ids;
		if((ids = namespaceIds.get(UniqueQName.XS.getNamespaceURI().toString())) == null)
			return null;

		return ids.get(id);
	}

	public $xs_ID($xs_ID<T> binding)
	{
		super(binding);
	}

	public $xs_ID(String value)
	{
		super(value);
		persist(_$$getName().getNamespaceURI(), value, this);
	}

	protected $xs_ID()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(String text)
	{
		final Object old = getText();
		super.setText(text);
		if(old != null)
			remove(_$$getName().getNamespaceURI(), old);

		persist(_$$getName().getNamespaceURI(), text, this);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		persist(parent.getNamespaceURI(), value, this);
		super.setText(value);
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_ID clone()
	{
		return new $xs_ID(this)
		{
			protected $xs_ID inherits()
			{
				return this;
			}
		};
	}
}
