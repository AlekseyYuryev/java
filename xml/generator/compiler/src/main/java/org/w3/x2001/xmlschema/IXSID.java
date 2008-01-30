package org.w3.x2001.xmlschema;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.safris.xml.generator.processor.phase.BindingQName;
import org.w3.x2001.xmlschema.IXSID;
import org.w3c.dom.Element;

public abstract class IXSID<T extends BindingType> extends IXSNCName<T>
{
	protected static final Map<String,Map<Object,IXSID>> namespaceIds = new HashMap<String,Map<Object,IXSID>>();

	private static void persist(String namespace, Object value, IXSID id)
	{
		Map<Object,IXSID> ids;
		if((ids = namespaceIds.get(namespace)) == null)
			namespaceIds.put(namespace, ids = new HashMap<Object,IXSID>());

		ids.put(value, id);
	}

	private static void remove(String namespace, Object value)
	{
		Map<Object,IXSID> ids;
		if((ids = namespaceIds.get(namespace)) == null)
			return;

		ids.remove(value);
	}

	public static IXSID lookupId(String id)
	{
		Map<Object,IXSID> ids;
		if((ids = namespaceIds.get(BindingQName.XS.getNamespaceURI().toString())) == null)
			return null;

		return ids.get(id);
	}

	public IXSID(IXSID<T> binding)
	{
		super(binding);
	}

	public IXSID(String value)
	{
		super(value);
		persist(_getName().getNamespaceURI(), value, this);
	}

	protected IXSID()
	{
		super();
	}

	protected Object getTEXT()
	{
		return super.getTEXT();
	}

	protected void setTEXT(String text)
	{
		final Object old = getTEXT();
		super.setTEXT(text);
		if(old != null)
			remove(_getName().getNamespaceURI(), old);

		persist(_getName().getNamespaceURI(), text, this);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		persist(element.getNamespaceURI(), value, this);
		super.setTEXT(value);
	}

	protected String _encode(Element parent) throws MarshalException
	{
		if(super.getTEXT() == null)
			return "";

		return super.getTEXT().toString();
	}

	public IXSID clone()
	{
		return new IXSID(this)
		{
			protected IXSID inherits()
			{
				return this;
			}
		};
	}
}
