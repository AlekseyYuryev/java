package org.safris.xml.generator.processor;

import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import org.safris.commons.xml.NamespaceURI;
import org.safris.commons.xml.Prefix;
import org.safris.xml.generator.processor.BindingQName;

public class BindingQName
{
	private static final Map<NamespaceURI,Prefix> namespaceURIToPrefix = new HashMap<NamespaceURI,Prefix>();
	private static final Map<QName,BindingQName> instances = new HashMap<QName,BindingQName>();

	// subjectively chosen
	private static final String W3C_XML_SCHEMA_PREFIX = "XS";
	private static final String W3C_XML_SCHEMA_INSTANCE_PREFIX = "xsi";

	public static final BindingQName XS = BindingQName.getInstance(new QName(XMLConstants.W3C_XML_SCHEMA_NS_URI, "", W3C_XML_SCHEMA_PREFIX));
	public static final BindingQName XSI = BindingQName.getInstance(new QName(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "", W3C_XML_SCHEMA_INSTANCE_PREFIX));

	// staticly defined
	public static final BindingQName XML = BindingQName.getInstance(new QName(XMLConstants.XML_NS_URI, "", XMLConstants.XML_NS_PREFIX));
	public static final BindingQName XMLNS = BindingQName.getInstance(new QName(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "", XMLConstants.XMLNS_ATTRIBUTE));

	public static BindingQName getInstance(QName name)
	{
		final BindingQName bindingQName = new BindingQName(name);
		BindingQName instance = instances.get(name);
		if(instance == null)
			instances.put(name, instance = bindingQName);

		return instance;
    }

	public static BindingQName getInstance(String namespaceURI, String localPart)
	{
		final QName name = new QName(namespaceURI, localPart);
        final BindingQName bindingQName = new BindingQName(name);
		BindingQName instance = instances.get(name);
		if(instance == null)
			instances.put(name, instance = bindingQName);

		return instance;
    }

	public static BindingQName getInstance(NamespaceURI namespaceURI, String localPart)
	{
		final QName name = new QName(namespaceURI.toString(), localPart);
        final BindingQName bindingQName = new BindingQName(new QName(namespaceURI.toString(), localPart));
		BindingQName instance = instances.get(name);
		if(instance == null)
			instances.put(name, instance = bindingQName);

		return instance;
    }

	public static BindingQName getInstance(String namespaceURI, String localPart, String prefix)
	{
		final QName name = new QName(namespaceURI, localPart);
        final BindingQName bindingQName = new BindingQName(new QName(namespaceURI, localPart, prefix));
		BindingQName instance = instances.get(name);
		if(instance == null)
			instances.put(name, instance = bindingQName);

		return instance;
	}

	public static BindingQName getInstance(NamespaceURI namespaceURI, String localPart, String prefix)
	{
		final QName name = new QName(namespaceURI.toString(), localPart);
        final BindingQName bindingQName = new BindingQName(new QName(namespaceURI.toString(), localPart, prefix));
		BindingQName instance = instances.get(name);
		if(instance == null)
			instances.put(name, instance = bindingQName);

		return instance;
	}

	private final String localPart;
	private final NamespaceURI namespaceURI;
	private Prefix prefix = null;

	private BindingQName(QName name)
	{
		if(name.getNamespaceURI() != null && !XMLConstants.NULL_NS_URI.equals(name.getNamespaceURI()))
		{
			this.namespaceURI = NamespaceURI.getInstance(name.getNamespaceURI());
			if(XMLConstants.XML_NS_URI.equals(name.getNamespaceURI()))
				this.prefix = Prefix.getInstance(XMLConstants.XML_NS_PREFIX);
			else if(XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(name.getNamespaceURI()))
				this.prefix = Prefix.getInstance(XMLConstants.XMLNS_ATTRIBUTE);
			else if(XMLConstants.W3C_XML_SCHEMA_NS_URI.equals(name.getNamespaceURI()))
				this.prefix = Prefix.getInstance(W3C_XML_SCHEMA_PREFIX);
			else if(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI.equals(name.getNamespaceURI()))
				this.prefix = Prefix.getInstance(W3C_XML_SCHEMA_INSTANCE_PREFIX);
			else if(name.getPrefix() != null)
				this.prefix = Prefix.getInstance(name.getPrefix());
			else
				this.prefix = null;
		}
		else
		{
			if(name.getPrefix() != null)
			{
				this.prefix = Prefix.getInstance(name.getPrefix());
				if(XMLConstants.XML_NS_PREFIX.equals(name.getPrefix()))
					this.namespaceURI = NamespaceURI.getInstance(XMLConstants.XML_NS_URI);
				else if(XMLConstants.XMLNS_ATTRIBUTE.equals(name.getPrefix()))
					this.namespaceURI = NamespaceURI.getInstance(XMLConstants.XMLNS_ATTRIBUTE_NS_URI);
				else if(W3C_XML_SCHEMA_PREFIX.equals(name.getPrefix()))
					this.namespaceURI = NamespaceURI.getInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
				else if(W3C_XML_SCHEMA_INSTANCE_PREFIX.equals(name.getPrefix()))
					this.namespaceURI = NamespaceURI.getInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
				else
					throw new IllegalArgumentException("Unknown prefix used: \"" + name.getPrefix() + "\"");
			}
			else
				throw new IllegalArgumentException("Both namespaceURI and prefix are null.");
		}

		this.localPart = name.getLocalPart();
	}

	public static void linkPrefixNamespace(NamespaceURI namespaceURI, Prefix prefix)
	{
		if(namespaceURI == null || XMLConstants.NULL_NS_URI.equals(namespaceURI.toString()) || prefix == null || XMLConstants.DEFAULT_NS_PREFIX.equals(prefix.toString()))
			return;

		final Prefix exists = namespaceURIToPrefix.get(namespaceURI);
		if(exists != null && !exists.equals(prefix))
			throw new Error("Prefix for namespace {" + namespaceURI + "} is being redefined from \"" + exists + "\" to \"" + prefix + "\"");

		namespaceURIToPrefix.put(namespaceURI, prefix);
	}

	public NamespaceURI getNamespaceURI()
	{
		return namespaceURI;
	}

	public String getLocalPart()
	{
		return localPart;
	}

	public Prefix getPrefix()
	{
		if(prefix != null && !XMLConstants.DEFAULT_NS_PREFIX.equals(prefix.toString()))
			return prefix;

		final Prefix exists = namespaceURIToPrefix.get(namespaceURI);
		if(exists != null && (prefix == null || XMLConstants.DEFAULT_NS_PREFIX.equals(prefix.toString())))
			return prefix = exists;
		else
			return prefix;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof BindingQName))
			return false;

		final BindingQName bindingQName = (BindingQName)obj;
		return (namespaceURI != null ? namespaceURI.equals(bindingQName.namespaceURI) : bindingQName.namespaceURI == null) && localPart.equals(bindingQName.localPart);
	}

	public int hashCode()
	{
		return namespaceURI != null ? namespaceURI.hashCode() : XMLConstants.NULL_NS_URI.hashCode() ^ localPart.hashCode();
	}

	public String toString()
	{
		if(namespaceURI == null || XMLConstants.NULL_NS_URI.equals(namespaceURI.toString()))
            return localPart;
		else
            return "{" + namespaceURI + "}" + localPart;
	}
}
