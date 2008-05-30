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

import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public abstract class Bindings
{
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
		final Binding binding = Binding.parseElement(element, null, null);
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
