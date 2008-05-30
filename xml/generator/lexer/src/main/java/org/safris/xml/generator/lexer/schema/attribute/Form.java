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

package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;
import org.safris.xml.generator.lexer.schema.attribute.Form;

public final class Form
{
	private static final Map<String,Form> enums = new HashMap<String,Form>();

	public static final Form QUALIFIED = new Form("qualified");
	public static final Form UNQUALIFIED = new Form("unqualified");

	public static Form parseForm(String value)
	{
		return enums.get(value);
	}

	private final String value;

	private Form(String value)
	{
		this.value = value;
		enums.put(value, this);
	}

	public String getValue()
	{
		return value;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(obj instanceof Form))
			return false;

		return getValue().equals(((Form)obj).getValue());
	}

	public int hashCode()
	{
		return getValue().hashCode();
	}

	public String toString()
	{
		return getValue();
	}
}
