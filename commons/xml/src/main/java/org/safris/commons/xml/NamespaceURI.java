/*  Copyright 2008 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.xml;

import java.util.HashMap;
import java.util.Map;

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
