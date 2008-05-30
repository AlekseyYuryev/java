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

package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.logging.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public final class BindingErrorHandler implements ErrorHandler
{
	private final Logger logger = Logger.getLogger(RuntimeLoggerName.VALIDATOR);
	private static final BindingErrorHandler instance = new BindingErrorHandler();

	public static BindingErrorHandler getInstance()
	{
		return instance;
	}

	// ignore fatal errors (an exception is guaranteed)
	public void fatalError(SAXParseException e) throws SAXException
	{
	}

	// treat validation errors as fatal
	public void error(SAXParseException e) throws SAXParseException
	{
		final String systemId = e.getSystemId() != null ? " systemId=\"" + e.getSystemId() + "\"" : "";
		logger.severe("[" + e.getLineNumber() + "," + e.getColumnNumber() + "]" + systemId);
		throw e;
	}

	// dump warnings too
	public void warning(SAXParseException e) throws SAXParseException
	{
		final String message = e.getMessage() != null ? " " + e.getMessage() : "";
		logger.warning("[" + e.getLineNumber() + "," + e.getColumnNumber() + "] systemId=\"" + e.getSystemId() + "\"" + message);
	}

	private BindingErrorHandler()
	{
	}
}
