package org.safris.commons.xml;

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
	public static String domToString(Element element, FormatOption ... options)
	{
		final FormatOption option = FormatOption.consolidate(options);
		final StringBuffer buffer = new StringBuffer();
		domToString(buffer, element, 0, option);
		return buffer.toString();
	}

	private static void domToString(StringBuffer stringBuffer, Node node, int depth, FormatOption option)
	{
		if(node == null)
			return;

		final String nodeName;
		if(option.isIgnoreNamespaces())
			nodeName = node.getLocalName();
		else
			nodeName = node.getNodeName();

		final String nodeValue = node.getNodeValue();
		final int type = node.getNodeType();
		if(Node.ELEMENT_NODE == type)
		{
			if(option.isIndent() && stringBuffer.length() > 1 && stringBuffer.charAt(stringBuffer.length() - 1) == '>')
			{
				stringBuffer.append("\n");
				for(int i = 0; i < depth; i++)
				{
					stringBuffer.append("\t");
				}
			}

			stringBuffer.append("<");
			stringBuffer.append(nodeName);
			attributesToString(stringBuffer, node, depth + 1, option);
			if(node.hasChildNodes())
			{
				stringBuffer.append(">");
				final NodeList nodeList = node.getChildNodes();
				for(int i = 0; i < nodeList.getLength(); i++)
				{
					domToString(stringBuffer, nodeList.item(i), depth + 1, option);
				}

				if(option.isIndent() && stringBuffer.length() > 1 && stringBuffer.charAt(stringBuffer.length() - 1) == '>')
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

	private static void attributesToString(StringBuffer stringBuffer, Node node, int depth, FormatOption option)
	{
		final NamedNodeMap namedNodeMap;
		if((namedNodeMap = node.getAttributes()) == null)
			return;

		for(int i = 0; i < namedNodeMap.getLength(); i++)
		{
			node = namedNodeMap.item(i);
			final String nodeName = node.getNodeName();
			if(nodeName.startsWith("xmlns") && option.isIgnoreNamespaces())
				continue;

			if(option.isIndent())
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
			entityConvert(stringBuffer, node.getNodeValue());
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
