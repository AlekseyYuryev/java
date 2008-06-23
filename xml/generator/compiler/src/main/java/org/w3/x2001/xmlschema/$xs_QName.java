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

import javax.xml.namespace.QName;
import org.safris.xml.generator.compiler.runtime.BindingType;
import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_QName<T extends BindingType> extends $xs_anySimpleType<T>
{
	public $xs_QName($xs_QName<T> binding)
	{
		super(binding);
	}

	public $xs_QName(QName value)
	{
		super();
	}

	protected $xs_QName()
	{
		super();
	}

	protected QName getTEXT()
	{
		return (QName)super.getText();
	}

	protected void setTEXT(QName text)
	{
		super.setText(text);
	}

	protected void _decode(Element element, String value) throws ParseException
	{
		final QName temp = stringToQName(value);
		super.setText(temp);
		if(element != null)
			super.setText(new javax.xml.namespace.QName(element.getOwnerDocument().getDocumentElement().lookupNamespaceURI(temp.getPrefix()), temp.getLocalPart()));
	}

	protected String _encode(Element parent) throws MarshalException
	{
		final QName value = (QName)super.getText();
		if(parent != null && value.getNamespaceURI() != null)
			return _$$getPrefix(parent, value) + ":" + value.getLocalPart();
		else
			return value.getLocalPart();
	}

	public $xs_QName clone()
	{
		return new $xs_QName(this)
		{
			protected $xs_QName inherits()
			{
				return this;
			}
		};
	}
}
