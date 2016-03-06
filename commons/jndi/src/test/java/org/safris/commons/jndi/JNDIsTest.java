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

package org.safris.commons.jndi;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class JNDIsTest extends LoggableTest {
  @Test
  @Ignore("Ignored because javax.transaction artifact is not in repo1.maven.org")
  public void testJNDIs() throws NamingException {
    final String name = "java:comp/env/jdbc/DAO";
    final String value = "Hello World";
    JNDIs.bind(name, value);

    final InitialContext context = new InitialContext();
    final String lookedup = (String)context.lookup(name);
    Assert.assertEquals(value, lookedup);
  }
}