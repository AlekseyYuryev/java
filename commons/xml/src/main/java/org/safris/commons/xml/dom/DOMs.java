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

package org.safris.commons.xml.dom;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DOMs
{
	/**
	 * Converts a DOM document to a XML string.
	 * It handles all children recursively.
	 *
	 * Note: this only handles elements, attributes and text nodes.
	 * It will not handle processing instructions, comments, CDATA or anything else.
	 *
	 * @param element element to convert.
	 */
	public static String domToString(Element element, DOMStyle ... styles)
	{
		final DOMStyle style = DOMStyle.consolidate(styles);
		final StringBuffer buffer = new StringBuffer();
		domToString(buffer, element, 0, style);
		return buffer.toString();
	}

	private static void domToString(StringBuffer stringBuffer, Node node, int depth, DOMStyle style)
	{
		if(node == null)
			return;

		final String nodeName;
		if(style.isIgnoreNamespaces())
			nodeName = node.getLocalName();
		else
			nodeName = node.getNodeName();

		final String nodeValue = node.getNodeValue();
		final int type = node.getNodeType();
		if(Node.ELEMENT_NODE == type)
		{
			if(style.isIndent() && stringBuffer.length() > 1 && stringBuffer.charAt(stringBuffer.length() - 1) == '>')
			{
				stringBuffer.append("\n");
				for(int i = 0; i < depth; i++)
				{
					stringBuffer.append("\t");
				}
			}

			stringBuffer.append("<");
			stringBuffer.append(nodeName);
			attributesToString(stringBuffer, node, depth + 1, style);
			if(node.hasChildNodes())
			{
				stringBuffer.append(">");
				final NodeList nodeList = node.getChildNodes();
				for(int i = 0; i < nodeList.getLength(); i++)
				{
					domToString(stringBuffer, nodeList.item(i), depth + 1, style);
				}

				if(style.isIndent() && stringBuffer.length() > 1 && stringBuffer.charAt(stringBuffer.length() - 1) == '>')
				{
					stringBuffer.append("\n");
					for(int i = 0; i < depth; i++)
					{
						stringBuffer.append("\t");
					}
				}
				stringBuffer.append("</").append(nodeName).append(">");
			}
			else
			{
				stringBuffer.append("/>");
			}
		}
		else if(Node.TEXT_NODE == type && nodeValue != null && nodeValue.length() != 0)
		{
			// Note: DOM expands entity references to their Unicode equivalent.
			// '&amp;' becomes simply '&'.  Since the string being constructed
			// here is intended to be used as XML text, we have to reconstruct
			// the standard entity references
			entityConvert(stringBuffer, nodeValue);
		}
	}

	private static void attributesToString(StringBuffer stringBuffer, Node node, int depth, DOMStyle style)
	{
		final NamedNodeMap attributes;
		if((attributes = node.getAttributes()) == null)
			return;

		for(int i = 0; i < attributes.getLength(); i++)
		{
			final Node attribute = attributes.item(i);
			final String nodeName = attribute.getNodeName();
			if(nodeName.startsWith("xmlns") && style.isIgnoreNamespaces())
				continue;

			if(style.isIndent())
			{
				stringBuffer.append("\n");
				for(int j = 0; j < depth; j++)
				{
					stringBuffer.append("\t");
				}
			}
			else
			{
				stringBuffer.append(" ");
			}

			stringBuffer.append(nodeName);
			stringBuffer.append("=\"");
			entityConvert(stringBuffer, attribute.getNodeValue());
			stringBuffer.append("\"");
		}
	}

	/**
	 * Convert the invalid XML characters in a string to
	 * character entities.
	 *
	 * @param textToConvert the String containing invalid entities.
	 * @return String with expanded entities.
	 */
	private static void entityConvert(StringBuffer stringBuffer, String entity)
	{
		if(entity == null)
			return;

		entity = entity.trim();
		for(int i = 0; i < entity.length(); i++)
		{
			switch(entity.charAt(i))
			{
				case '&':
					stringBuffer.append("&amp;");
					break;
				case '>':
					stringBuffer.append("&gt;");
					break;
				case '<':
					stringBuffer.append("&lt;");
					break;
				default:
					stringBuffer.append(entity.substring(i, i + 1));
					break;
			}
		}
	}

	private DOMs()
	{
	}
}
