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

package org.safris.commons.xml.binding;

import java.util.HashMap;
import java.util.Map;

public class Language implements CharSequence
{
	public static Language parseLanguage(String string)
	{
		return instances.get(string);
	}

	private static final Map<String,Language> instances = new HashMap<String,Language>();
	private final String value;

	public Language(String value)
	{
		this.value = value;
		instances.put(value, this);
	}

	public int length()
	{
		// TODO: Implement this method
		return 0;
	}

	public char charAt(int index)
	{
		// TODO: Implement this method
		return 0;
	}

	public CharSequence subSequence(int start, int end)
	{
		// TODO: Implement this method
		return null;
	}
}
