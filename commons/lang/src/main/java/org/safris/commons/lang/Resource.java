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

package org.safris.commons.lang;

import java.net.URL;

public final class Resource
{
	private final URL url;
	private final ClassLoader classLoader;

	public Resource(URL url, ClassLoader classLoader)
	{
		this.url = url;
		this.classLoader = classLoader;
	}

	public URL getURL()
	{
		return url;
	}

	public ClassLoader getClassLoader()
	{
		return classLoader;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof Resource))
			return false;

		final Resource resource = (Resource)obj;
		return url != null ? url.equals(resource.url) && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null) : resource.url == null && (classLoader != null ? classLoader.equals(resource.classLoader) : resource.classLoader == null);
	}

	public int hashCode()
	{
		return url.hashCode() * 3 + classLoader.hashCode();
	}
}
