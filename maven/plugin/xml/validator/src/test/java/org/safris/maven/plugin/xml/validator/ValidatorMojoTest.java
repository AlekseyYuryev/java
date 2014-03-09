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

package org.safris.maven.plugin.xml.validator;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ValidatorMojoTest {
  public static void main(final String[] args) throws Exception {
    new ValidatorMojoTest().testValidate();
  }

  @Test
  public void testValidate() throws Exception {
    final File dir = new File(".");
    ValidatorMojo.validate(dir, new File("src/test/resources/xml/valid.xml"), null);
    try {
      ValidatorMojo.validate(dir, new File("src/test/resources/xml/invalid.xml"), null);
    }
    catch (final SAXException e) {
      if (!"cvc-datatype-valid.1.2.1: 'a' is not a valid value for 'integer'.".equals(e.getMessage()))
        fail(e.getMessage());
    }

    try {
      ValidatorMojo.validate(dir, new File("src/test/resources/xsd/test.xsd"), null);
    }
    catch (final SAXException e) {
      if (e.getMessage() != null && e.getMessage().startsWith("schema_reference.4: Failed to read schema document 'http://www.w3.org/2001/"))
        System.err.println(e.getMessage());
      else
        fail(e.getMessage());
    }
  }
}