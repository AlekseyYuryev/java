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

package org.safris.xml.generator.compiler.runtime;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;

public abstract class NotationType extends Binding<NotationType> implements BindingType
{
	private static final Map<String,NotationType> notations = new HashMap<String,NotationType>(7);

	protected static void _$$registerNotation(NotationType notation)
	{
		notations.put(notation.getName(), notation);
	}

	public static NotationType parseNotation(String name)
	{
		return notations.get(name);
	}

	private final QName _$$name;

	protected NotationType()
	{
		this._$$name = new QName(getName());
		notations.put(getName(), this);
	}

	protected Binding inherits()
	{
		return this;
	}

	protected abstract String getName();
	protected abstract String getPublic();
	protected abstract String getSystem();
}
