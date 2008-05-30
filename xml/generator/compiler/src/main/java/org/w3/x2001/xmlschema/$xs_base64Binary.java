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

import org.safris.commons.xml.binding.Base64Binary;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class $xs_base64Binary<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_base64Binary($xs_base64Binary<T> binding)
	{
		super(binding);
	}

	public $xs_base64Binary(Base64Binary value)
	{
		super(value);
	}

	protected $xs_base64Binary()
	{
		super();
	}

	protected Object getText()
	{
		return super.getText();
	}

	protected void setText(Base64Binary text)
	{
		super.setText(text);
	}

	protected void _$$decode(Element parent, String value) throws ParseException
	{
		super.setText(Base64Binary.parseBase64Binary(String.valueOf(value)));
	}

	protected String _$$encode(Element parent) throws MarshalException
	{
		if(super.getText() == null)
			return "";

		return super.getText().toString();
	}

	public $xs_base64Binary clone()
	{
		return new $xs_base64Binary(this)
		{
			protected $xs_base64Binary inherits()
			{
				return this;
			}
		};
	}
}
