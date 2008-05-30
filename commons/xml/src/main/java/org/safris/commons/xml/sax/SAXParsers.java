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

package org.safris.commons.xml.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLReaderFactory;

public final class SAXParsers
{
	public static SAXParser createParser() throws SAXException
	{
		// Create a specific SAXParser so that it works with our XMLSchemaResolver
		return new SAXParser(XMLReaderFactory.createXMLReader(com.sun.org.apache.xerces.internal.parsers.SAXParser.class.getName()));
	}

	private SAXParsers()
	{
	}
}
