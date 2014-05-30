package org.safris.commons.jndi;

import static org.junit.Assert.assertEquals;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Ignore;
import org.junit.Test;

public final class JNDIsTest {
  public static void main(final String[] args) throws NamingException {
    new JNDIsTest().testJNDIs();
  }

  @Test
  @Ignore("Ignored because javax.transaction artifact is not in repo1.maven.org")
  public void testJNDIs() throws NamingException {
    final String name = "java:comp/env/jdbc/DAO";
    final String value = "Hello World";
    JNDIs.bind(name, value);

    final InitialContext context = new InitialContext();
    final String lookedup = (String)context.lookup(name);
    assertEquals(value, lookedup);
  }
}