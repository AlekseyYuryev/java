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
