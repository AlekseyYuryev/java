package org.safris.commons.util.xml;

import java.util.HashMap;
import java.util.Map;
import org.safris.commons.util.NamespaceBinding;
import org.safris.commons.util.PackageName;

public final class NamespaceURI
{
	private static final Map<String,NamespaceURI> instances = new HashMap<String,NamespaceURI>();

	public static NamespaceURI getInstance(String namespaceURI)
	{
		NamespaceURI value = instances.get(namespaceURI);
		if(value == null)
			instances.put(namespaceURI, value = new NamespaceURI(namespaceURI));

		return value;
	}

	private final String namespaceURI;
	private final PackageName packageName;

	private NamespaceURI(String namespaceURI)
	{
		this.namespaceURI = namespaceURI;
		this.packageName = new PackageName(NamespaceBinding.getPackageFromNamespace(namespaceURI));
	}

	public PackageName getPackageName()
	{
		return packageName;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof NamespaceURI))
			return false;

		return namespaceURI.equals(((NamespaceURI)obj).namespaceURI);
	}

	public int hashCode()
	{
		return namespaceURI.hashCode();
	}

	public String toString()
	{
		return namespaceURI;
	}
}
