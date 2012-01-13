/*  Copyright Safris Software 2008
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

import java.io.File;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.junit.Test;
import org.safris.commons.xml.dom.DOMParsers;
import org.safris.commons.xml.validator.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.Assert.*;

public class BindingValidatorTest {
  public static void main(String[] args) throws Exception {
    new BindingValidatorTest().testSAXParser();
  }

  /**
   * This test verifies that the correct implementation of the SAXParser is used
   * within the validator. A SAXParser implementation other than the default
   * will cause a ClassCastException stating that
   * org.safris.xml.generator.compiler.runtime.XMLSchemaResolver cannot be cast
   * to org.apache.xerces.xni.parser.XMLEntityResolver.
   *
   * @exception Exception If any <code>Exception</code> is thrown.
   */
  @Test
  public void testSAXParser() throws Exception {
    System.setProperty("org.xml.sax.driver", SAXParser.class.getName());
    final Document document = DOMParsers.newDocumentBuilder().parse(new File("src/test/resources/xml/empty.xml"));
    if (document == null)
      fail("document == null");

    final Element element = document.getDocumentElement();
    try {
      new BindingValidator().parse(element);
    }
    catch (ValidationException e) {
      if (e.getMessage().startsWith(BindingEntityResolver.class.getName() + " cannot be cast to " + XMLEntityResolver.class.getName()))
        fail(e.getMessage());
      else if (e.getCause() == null || e.getCause().getMessage() == null || !e.getCause().getMessage().startsWith("cvc-elt.1: Cannot find the declaration of element"))
        throw e;
    }
  }
}
