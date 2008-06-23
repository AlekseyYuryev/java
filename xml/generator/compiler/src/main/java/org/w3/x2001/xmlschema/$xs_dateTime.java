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

package org.w3.x2001.xmlschema;

import org.safris.commons.xml.binding.DateTime;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class represents the Java binding of the dateTime instance of time.
 *
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#dateTime">Definition</a>
 */
public abstract class $xs_dateTime<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_dateTime($xs_dateTime<T> binding)
	{
		super(binding);
	}

	public $xs_dateTime(DateTime value)
	{
		super(value);
	}

	/**
	 * Allocates a <code>Date</code> object and initializes it so that it
	 * represents the time at which it was allocated. Milliseconds are
	 * <b>NOT</b> significant figures and are not represented.
	 *
	 * @see java.lang.System#currentTimeMillis()
	 */
	protected $xs_dateTime()
	{
		super();
	}

	protected DateTime getText()
	{
		return (DateTime)super.getText();
	}

	protected void setText(DateTime text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(DateTime.parseDateTime(value));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_dateTime clone()
	{
		return new $xs_dateTime(this)
		{
			protected $xs_dateTime inherits()
			{
				return this;
			}
		};
	}
}
