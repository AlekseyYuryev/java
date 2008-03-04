package org.safris.xml.generator.compiler.runtime;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.Resource;
import org.safris.commons.lang.Resources;
import org.safris.commons.net.URLs;
import org.safris.commons.xml.NamespaceBinding;
import org.safris.commons.xml.validator.XMLSchemaResolver;

public abstract class AbstractBinding implements Cloneable
{
	protected static final QName XSI_TYPE = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");
	protected static final QName XMLNS = new QName("http://www.w3.org/2000/xmlns/", "xmlns");
	protected static final QName XML = new QName("http://www.w3.org/XML/1998/namespace", "xml");

	private static final Map<QName,Class<? extends Binding>> elementBindings = new HashMap<QName,Class<? extends Binding>>();
	private static final Map<QName,Class<? extends Binding>> typeBindings = new HashMap<QName,Class<? extends Binding>>();

	protected static void _registerSchemaLocation(String namespaceURI, Class className, String schemaReference)
	{
		final String simpleName = className.getName().replace('.', '/') + ".class";
		final Resource resource = Resources.getResource(simpleName);
		if(resource == null)
			throw new BindingError("Cannot register: systemId=\"" + namespaceURI + "\"\n\tclassName=\"" + className.getName() + "\"\n\tschemaReference=\"" + schemaReference + "\"");

		final URL parent = URLs.getParent(resource.getURL());
		try
		{
			XMLSchemaResolver.registerSchemaLocation(namespaceURI, new URL(parent + "/" + schemaReference));
		}
		catch(MalformedURLException e)
		{
			System.err.println("[ERROR] Cannot register: systemId=\"" + namespaceURI + "\"\n\tclassName=\"" + className.getName() + "\"\n\tschemaReference=\"" + schemaReference + "\"");
		}
	}

	protected static void _registerElement(QName name, Class<? extends Binding> cls)
	{
		elementBindings.put(name, cls);
	}

	private static void loadPackage(String namespaceURI)
	{
		// FIXME: Look this over. Also make a dedicated RuntimeException for this.
		try
		{
			PackageLoader.getSystemPackageLoader().loadPackage(NamespaceBinding.getPackageFromNamespace(namespaceURI));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected static Class<? extends Binding> lookupElement(QName name)
	{
		Class<? extends Binding> clazz = elementBindings.get(name);
		if(clazz != null)
			return clazz;

		loadPackage(name.getNamespaceURI());
		return elementBindings.get(name);
	}

	protected static void _registerType(QName name, Class<? extends Binding> cls)
	{
		typeBindings.put(name, cls);
	}

	protected static Class<? extends Binding> lookupType(QName name)
	{
		Class<? extends Binding> clazz = typeBindings.get(name);
		if(clazz != null)
			return clazz;

		loadPackage(name.getNamespaceURI());
		return typeBindings.get(name);
	}

	protected static Object _getTEXT(Binding<SimpleType> binding)
	{
		return binding.getTEXT();
	}

	protected static QName stringToQName(java.lang.String name)
	{
		if(name == null || name.length() == 0)
			return null;

		int index = name.indexOf(":");
		if(index != -1)
			return new QName(null, name.substring(index + 1), name.substring(0, index));

		return new QName(name);
	}

	protected static String parsePrefix(String name)
	{
		if(name == null)
			return null;

		int index = name.indexOf(":");
		if(index != -1)
			return name.substring(0, index);

		return null;
	}

	protected static String parseLocalName(String name)
	{
		if(name == null)
			return null;

		int start = name.indexOf("{");
		if(start != -1)
		{
			int end = name.indexOf("}", start);
			if(end != -1)
			{
				return name.substring(end + 1);
			}
		}

		start = name.indexOf(":");
		if(start != -1)
			return name.substring(start + 1);

		return name;
	}
}
