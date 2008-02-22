package org.safris.xml.generator.compiler.runtime;

import org.safris.xml.generator.compiler.util.Validator;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public abstract class Bindings
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
	public static String domToString(Element element, BindingsOption ... options)
	{
		final BindingsOption option = BindingsOption.consolidate(options);
		final StringBuffer buffer = new StringBuffer();
		domToString(buffer, element, 0, option);
		return buffer.toString();
	}

	private static void domToString(StringBuffer stringBuffer, Node node, int depth, BindingsOption option)
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

	private static void attributesToString(StringBuffer stringBuffer, Node node, int depth, BindingsOption option)
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

	/**
	 * Marshals a Binding instance to an Element object.
	 *
	 * @param binding Binding instance to marshal.
	 * @return Element DOM object.
	 */
	public static Element marshal(Binding binding) throws MarshalException, ValidationException
	{
		if(binding.inherits() == null)
			throw new MarshalException("Binding must inherit from an instantiable element or attribute to be marshaled!");

		return binding.marshal();
	}

	/**
	 * Parse an Element object to a Binding instance.
	 *
	 * @param element Element object to parse.
	 * @return Binding instance.
	 */
	public static Binding parse(Element element) throws ParseException, ValidationException
	{
		final Binding binding = Binding.parseElement((Element)element.cloneNode(true), null, null);
		if(Validator.getSystemValidator() != null)
			Validator.getSystemValidator().validateParse(element);

		return binding;
	}

	/**
	 * Parse an InputSource pointing to xml into a Binding instance.
	 *
	 * @param inputSource InputSource pointing to xml.
	 * @return Binding instance.
	 */
	public static Binding parse(InputSource inputSource) throws ParseException, ValidationException
	{
		final Element element;
		try
		{
			element = Binding.newDocumentBuilder().parse(inputSource).getDocumentElement();
		}
		catch(Exception e)
		{
			throw new ParseException(e);
		}

		if(Validator.getSystemValidator() != null)
			Validator.getSystemValidator().validateParse(element);

		return Binding.parseElement(element, null, null);
	}
}
