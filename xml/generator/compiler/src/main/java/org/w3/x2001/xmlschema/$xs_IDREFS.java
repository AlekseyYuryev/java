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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_IDREFS<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_IDREFS($xs_IDREFS<T> binding)
	{
		super(binding);
	}

	public $xs_IDREFS(List<String> value)
	{
		super(value);
	}

	protected $xs_IDREFS()
	{
		super();
	}

	protected List<String> getText()
	{
		return (List<String>)super.getText();
	}

	protected void setText(List<String> text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		if(value == null || value.length() == 0)
			return;

		super.setText(new ArrayList<String>());
		final StringTokenizer tokenizer = new StringTokenizer(String.valueOf(value));
		while(tokenizer.hasMoreTokens())
			((List<String>)super.getText()).add(tokenizer.nextToken());
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null || ((List)super.getText()).size() == 0)
			return null;

		String value = "";
		for(String temp : (List<String>)super.getText())
			value += " " + temp;

		return value.substring(1);
	}

	public $xs_IDREFS clone()
	{
		return new $xs_IDREFS(this)
		{
			protected $xs_IDREFS inherits()
			{
				return this;
			}
		};
	}
}
