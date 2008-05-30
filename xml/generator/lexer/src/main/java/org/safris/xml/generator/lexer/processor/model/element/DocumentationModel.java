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

package org.safris.xml.generator.lexer.processor.model.element;

import org.safris.xml.generator.lexer.processor.model.Model;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentationModel extends Model
{
	private String source = null;
	private String lang = null;
	private String text = " ";

	protected DocumentationModel(Node node, Model parent)
	{
		super(node, parent);
		final NamedNodeMap attributes = node.getAttributes();
		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			if(attribute.getNodeValue() == null)
				continue;

			if("source".equals(attribute.getLocalName()))
				source = attribute.getNodeValue();
			else if("lang".equals(attribute.getLocalName()))
				lang = attribute.getNodeValue();
		}

		final NodeList nodes = node.getChildNodes();
		for(int i = 0; i < nodes.getLength(); i++)
			if(Node.TEXT_NODE == nodes.item(i).getNodeType() && nodes.item(i).getNodeValue().trim().length() != 0)
				text += "\n" + nodes.item(i).getNodeValue();

		if(text.length() > 1)
			this.text = text.substring(1);
	}

	public final String getText()
	{
		return text;
	}

	public final String getSource()
	{
		return source;
	}

	public final String getLang()
	{
		return lang;
	}

	public final void merge(DocumentationModel model)
	{
		this.text += "\n" + model.text;
	}
}
