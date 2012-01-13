/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.xml.generator.compiler.runtime;

import org.safris.commons.xml.validator.ValidationException;
import org.safris.commons.xml.validator.Validator;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public abstract class Bindings {
  /**
   * Marshals a Binding instance to an Element object.
   *
   * @param binding Binding instance to marshal.
   * @return Element DOM object.
   */
  public static Element marshal(Binding binding) throws MarshalException, ValidationException {
    if (binding.inherits() == null)
      throw new MarshalException("Binding must inherit from an instantiable element or attribute to be marshaled!");

    return binding.marshal();
  }

  /**
   * Parse an Element object to a Binding instance.
   *
   * @param element Element object to parse.
   * @return Binding instance.
   */
  public static Binding parse(Element element) throws ParseException, ValidationException {
    final Binding binding = Binding.parseElement(element, null);
    if (Validator.getSystemValidator() != null)
      Validator.getSystemValidator().validateParse(element);

    return binding;
  }

  /**
   * Parse an InputSource pointing to xml into a Binding instance.
   *
   * @param inputSource InputSource pointing to xml.
   * @return Binding instance.
   */
  public static Binding parse(InputSource inputSource) throws ParseException, ValidationException {
    final Element element;
    try {
      element = Binding.newDocumentBuilder().parse(inputSource).getDocumentElement();
    }
    catch (Exception e) {
      throw new ParseException(e);
    }

    if (Validator.getSystemValidator() != null)
      Validator.getSystemValidator().validateParse(element);

    return Binding.parseElement(element, null);
  }
}
