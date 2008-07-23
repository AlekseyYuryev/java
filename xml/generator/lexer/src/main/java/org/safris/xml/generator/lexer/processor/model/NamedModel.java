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

package org.safris.xml.generator.lexer.processor.model;

import org.safris.xml.generator.lexer.lang.UniqueQName;
import org.safris.xml.generator.lexer.processor.Nameable;
import org.safris.xml.generator.lexer.processor.model.element.RestrictionModel;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class NamedModel extends Model implements Nameable<Model>
{
	private UniqueQName name = null;

	protected NamedModel(Node node, Model parent)
	{
		super(node, parent);
		if(node == null)
			return;

		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if("name".equals(attribute.getLocalName()))
				name = UniqueQName.getInstance(getTargetNamespace(), attribute.getNodeValue());
		}
	}

	protected final void setName(UniqueQName name)
	{
		this.name = name;
	}

	public UniqueQName getName()
	{
		return name;
	}

	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;

		if(!(getClass().isInstance(obj)))
			return false;

		final NamedModel that = (NamedModel)obj;
		return name != null ? name.equals(that.name) : that.name == null;
	}

	// FIXME: This is dirty!!
	public static UniqueQName getNameOfRestrictionBase(NamedModel model)
	{
		if(model == null)
			return null;

		for(Model child : model.getChildren())
		{
			if(!(child instanceof RestrictionModel))
				continue;

			return ((RestrictionModel)child).getBase().getName();
		}

		return null;
	}

	public int hashCode()
	{
		UniqueQName name = this.name;
		if(name == null)
			name = getNameOfRestrictionBase(this);

		return 3 * (name != null ? name.hashCode() : -1);
	}

	public String toString()
	{
		UniqueQName name = this.name;
		if(name == null)
			name = getNameOfRestrictionBase(this);

		if(name == null)
			return super.toString();

		return super.toString() + name.toString();
	}
}
