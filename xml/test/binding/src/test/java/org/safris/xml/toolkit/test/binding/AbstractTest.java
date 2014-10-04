/* Copyright (c) 2008 Seva Safris
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
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
