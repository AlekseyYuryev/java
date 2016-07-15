/* Copyright (c) 2006 Seva Safris
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

package org.safris.ant.task.xsb;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class AntPropertyResolverTest extends LoggableTest {
  @Test
  public void testAntPropertyResolver() {
    final AntPropertyResolver antPropertyResolver = new AntPropertyResolver(null);
    Assert.assertNull(antPropertyResolver.resolve(null));

    String arg = "text with ${something} here";
    Assert.assertEquals(arg, antPropertyResolver.resolve(arg));

    arg = "text with something";
    Assert.assertEquals(arg, antPropertyResolver.resolve(arg));

    arg = "text with ${something";
    Assert.assertEquals(arg, antPropertyResolver.resolve(arg));
  }
}