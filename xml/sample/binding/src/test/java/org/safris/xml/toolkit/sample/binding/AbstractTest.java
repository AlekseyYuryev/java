/*  Copyright 2010 Safris Technologies Inc.
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

package org.safris.xml.toolkit.sample.binding;

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
