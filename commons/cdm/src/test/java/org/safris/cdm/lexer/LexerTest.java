/* Copyright (c) 2014 Seva Safris
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

package org.safris.cdm.lexer;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.safris.cdm.Audit;
import org.safris.commons.io.Files;
import org.safris.commons.test.LoggableTest;

public class LexerTest extends LoggableTest {
  @Test
  public void testTokenize() throws IOException {
    final File file = new File("../../cf/xsb/runtime/src/main/java/org/safris/cf/xsb/runtime/Binding.java");
    final Audit audit = Lexer.tokenize(file);

    final String expected = new String(Files.getBytes(file));
    final String out = audit.toString();
    Assert.assertEquals(expected, out);
    /*for (int x = 0; x < indices.size(); x++) {
      final Index index = indices.get(x);
      log(Strings.padFixed(index.token + ":", 16) + new String(bytes, index.start, index.length + 1));
    }*/
  }
}