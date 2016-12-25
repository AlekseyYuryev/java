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

package org.safris.maven.plugin.xml.validator;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;
import org.xml.sax.SAXException;

public class ValidatorMojoTest extends LoggableTest {
  @Test
  public void testValidate() throws Exception {
    final File dir = new File(".");
    ValidatorMojo.validate(dir, new File("src/test/resources/valid.xml"), false);
    try {
      ValidatorMojo.validate(dir, new File("src/test/resources/invalid.xml"), true);
      Assert.fail("Should have failed.");
    }
    catch (final SAXException e) {
      if (!"cvc-datatype-valid.1.2.1: 'a' is not a valid value for 'integer'.".equals(e.getMessage()))
        Assert.fail(e.getMessage());
    }

    try {
      ValidatorMojo.validate(dir, new File("src/test/resources/test.xsd"), true);
    }
    catch (final SAXException e) {
      System.err.println(e.getMessage());
      if (e.getMessage() != null && e.getMessage().startsWith("schema_reference.4: Failed to read schema document 'http://www.w3.org/2001/"))
        log(e.getMessage());
      else
        Assert.fail(e.getMessage());
    }
  }
}