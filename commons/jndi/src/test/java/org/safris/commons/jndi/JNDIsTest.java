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