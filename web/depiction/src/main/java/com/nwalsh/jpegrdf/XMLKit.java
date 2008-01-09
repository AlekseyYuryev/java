/*
 * $Id: XMLKit.java,v 1.4 2005/04/27 10:20:07 nwalsh Exp $
 */
package com.nwalsh.jpegrdf;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class XMLKit
{

	public static void checkPrefix(String qname)
	{
		if(qname == null)
		{
			SystemKit.abort(1, "Unexpected null QName");
		}

		if(qname.indexOf(":") <= 0)
		{
			SystemKit.abort(
			1,
			"Missing prefix: " + qname);
		}
	}

	public static void checkQName(String qname)
	{
		XMLKit.checkPrefix(qname);
		if(qname.indexOf(":") == qname.length())
		{
			SystemKit.abort(
			1,
			"Missing local name: " + qname);
		}
	}

	/**
	 * Determine if the specified string satisfies the constraints of an XML Name.
	 *
	 * This code is seriously incomplete.
	 */
	public static boolean isXMLName(String str)
	{
		char name[] = str.toCharArray();
		if(name[0] == '_'
		|| Character.isLetter(name[0]))
		{
			for(int pos = 0; pos < name.length; pos++)
			{
				if(!Character.isLetter(name[pos])
				&& !Character.isDigit(name[pos])
				&& name[pos] != '.'
				&& name[pos] != '-'
				&& name[pos] != '_')
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static String xmlDecode(String text)
	{
		String origText = text;
		String newText = "";
		while(text.indexOf("&") >= 0)
		{
			int pos = text.indexOf("&");
			newText += text.substring(0, pos);
			text = text.substring(pos + 1);
			pos = text.indexOf(";");
			if(pos <= 0)
			{
				SystemKit.abort(
				1,
				"Improperly escaped character: "
				+ origText);
			}
			String charref = text.substring(0, pos);
			text = text.substring(pos + 1);

			if(charref.equals("lt"))
			{
				newText += "<";
			}
			else if(charref.equals("gt"))
			{
				newText += ">";
			}
			else if(charref.equals("amp"))
			{
				newText += "&";
			}
			else if(charref.equals("quot"))
			{
				newText += "\"";
			}
			else if(charref.equals("apos"))
			{
				newText += "'";
			}
			else if(charref.startsWith("#"))
			{
				String number = charref.substring(1);
				int radix = 10;

				if(charref.startsWith("#x")
				|| charref.startsWith("#X"))
				{
					number = charref.substring(2);
					radix = 16;
				}

				if("".equals(number))
				{
					SystemKit.abort(
					1,
					"Improperly escaped character: "
					+ charref);
				}

				char ch = 0;
				try
				{
					ch =
					(char) Integer.parseInt(
					number,
					radix);
				}
				catch(NumberFormatException nfe)
				{
					SystemKit.abort(
					1,
					"Improperly escaped character: "
					+ charref);
				}
				newText += ch;
			}
			else
			{
				SystemKit.abort(
				1,
				"Improperly escaped character: "
				+ charref);
			}
		}

		return newText + text;
	}

	public static void getNamespaces(String xmlFile)
	{
		// Construct a SAX Parser using JAXP
		SAXParserFactory factory = SAXParserFactory.newInstance();

		// For this app, namespaces and validity are irrelevant
		factory.setNamespaceAware(true);
		factory.setValidating(false);

		// Our handler will actually count the words
		PrefixGrabber handler = new PrefixGrabber();

		try
		{
			// Construct the parser and
			SAXParser parser = factory.newSAXParser();
			// use it to parse the document
			parser.parse(xmlFile, handler);
		}
		catch(Exception e)
		{
			// Maybe FileNotFound, maybe something else, anyway, life goes
			// on...
			return;
		}

		// Add any newly discovered prefixes to the namespace bindings
		Hashtable docNamespaces = handler.getNamespaces();
		Enumeration document = docNamespaces.keys();
		while(document.hasMoreElements())
		{
			String prefix = (String)document.nextElement();
			if(!RDFKit.namespaces.containsKey(prefix))
			{
				RDFKit.namespaces.put(prefix, (String) docNamespaces.get(prefix));
			}
		}
	}

	public static String xmlEncode(String rawtext)
	{
		// Now turn that UTF-8 string into something "safe"
		String rdfString =
		"<?xml version='1.0' encoding='ISO-8859-1'?>\n";
		char[] sbuf = rawtext.toCharArray();

		int lastPos = 0;
		int pos = 0;
		while(pos < sbuf.length)
		{
			char ch = sbuf[pos];

			if(ch == '\n' || (ch >= ' ' && ch <= '~'))
			{
				// nop;
			}
			else
			{
				if(pos > lastPos)
				{
					String range =
					new String(
					sbuf,
					lastPos,
					pos - lastPos);
					rdfString += range;
				}
				rdfString += "&#" + (int) ch + ";";
				lastPos = pos + 1;
			}
			pos++;
		}
		if(pos > lastPos)
		{
			String range =
			new String(sbuf, lastPos, pos - lastPos);
			rdfString += range;
		}

		return rdfString;
	}

}
