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

package org.safris.maven.plugin.xdb.xde;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.safris.commons.lang.Resources;
import org.safris.commons.test.LoggableTest;
import org.safris.xdb.entities.generator.Generator;

public class EntityGeneratorTest extends LoggableTest {
  public static void main(final String[] args) throws Exception {
    new EntityGeneratorTest().test();
  }

  @Test
  @Ignore
  public void test() throws Exception {
    Generator.generate(Resources.getResource("survey.xdl").getURL(), new File("target/generated-test-sources/xde"));
  }
}