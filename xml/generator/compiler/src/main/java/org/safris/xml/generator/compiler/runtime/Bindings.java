package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.xml.validation.ValidationException;
import org.safris.commons.xml.validation.Validator;
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
