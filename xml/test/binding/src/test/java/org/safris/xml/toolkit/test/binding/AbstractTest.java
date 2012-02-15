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

package org.safris.xml.toolkit.test.binding;

import java.io.StringReader;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.commons.xml.validator.Validator;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.BindingValidator;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public abstract class AbstractTest {
  static {
    final Validator validator = new BindingValidator();
    Validator.setSystemValidator(validator);
  }

  protected static boolean verifyBinding(Binding binding) {
    boolean success = true;
    try {
      Element element = Bindings.marshal(binding);
      String xml = DOMs.domToString(element, DOMStyle.INDENT);
      System.out.println(xml + "\n");
      Binding reparsed = Bindings.parse(new InputSource(new StringReader(xml)));
      String message = "SUCCESS";
      String not = "---";
      if (!binding.equals(reparsed)) {
        success = false;
        message = "FAILURE";
        not = "NOT";
      }

      System.out.println("[INFO] java -> xml -> java           Object equals() " + message);
      System.out.println("        ^-" + not + "-equal----^\n");

      Validator.getSystemValidator().validate(element);

      System.out.println("[INFO]         xml              Validator.validate() " + message);
      System.out.println("                ^\n");

      message = "SUCCESS";
      not = "---";
      String xml2 = DOMs.domToString(Bindings.marshal(reparsed), DOMStyle.INDENT);
      if (!xml.equals(xml2)) {
        System.out.println(xml2);
        success = false;
        message = "FAILURE";
        not = "NOT";
      }

      System.out.println("[INFO] java -> xml -> java -> xml    String equals() " + message);
      System.out.println("                ^-" + not + "-equal----^\n");
    }
    catch (Exception e) {
      e.printStackTrace();
      success = false;
      System.err.print("A " + e.getClass().getSimpleName() + " has occured.");
    }

    return success;
  }

  public abstract void testExample() throws Exception;
}
