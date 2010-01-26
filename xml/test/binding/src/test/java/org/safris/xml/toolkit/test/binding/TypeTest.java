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

package org.safris.xml.toolkit.test.binding;

import com.safris.schema.test.$te_complexD;
import com.safris.schema.test.te_elemD;
import java.io.StringReader;
import org.junit.Test;
import org.safris.commons.lang.Strings;
import org.safris.commons.xml.dom.DOMStyle;
import org.safris.commons.xml.dom.DOMs;
import org.safris.xml.generator.compiler.runtime.Binding;
import org.safris.xml.generator.compiler.runtime.Bindings;
import org.safris.xml.toolkit.test.binding.regression.Metadata;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

public class TypeTest extends Metadata {
    private static final String DEFAULT_HOST = "aol-3";
    private static final String DEFAULT_dOMAIN = "liberty-iop.biz";
    private static String host = DEFAULT_HOST;
    private static String domain = DEFAULT_dOMAIN;

    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            host = args[0];
            domain = args[1];
        }

        new TypeTest().testType();
    }

    @Test
    public void testType() throws Exception {
        $te_complexD xsiType = new $te_complexD()
        {
            protected $te_complexD inherits() {
                return null;
            }
        };
        xsiType.set_a_attr1$(new $te_complexD._a_attr1$(Strings.getRandomString(8)));
        xsiType.set_a_attr2$(new $te_complexD._a_attr2$(Strings.getRandomString(8)));
        xsiType.set_c_attr1$(new $te_complexD._c_attr1$(Strings.getRandomString(8)));
        xsiType.set_c_attr2$(new $te_complexD._c_attr2$(Strings.getRandomString(8)));
        xsiType.set_d_attr1$(new $te_complexD._d_attr1$(Strings.getRandomString(8)));
        xsiType.set_d_attr2$(new $te_complexD._d_attr2$(Strings.getRandomString(8)));

        te_elemD elemD = new te_elemD();
        elemD.addte_elemC(xsiType);

        String marshalled = DOMs.domToString(elemD.marshal(), DOMStyle.INDENT);
        Binding binding = Bindings.parse(new InputSource(new StringReader(marshalled)));
        String remarshalled = DOMs.domToString(Bindings.marshal(binding), DOMStyle.INDENT);
        assertEquals(marshalled, remarshalled);
    }
}
