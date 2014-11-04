package org.safris.commons.lang;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class ClassLoadersTest {
  @Test
  public void test() {
    final URL[] urls = ClassLoaders.getClassPath();
    for (final URL url : urls)
      if (url.toExternalForm().contains("junit"))
        return;

    Assert.fail("Expected junit in the classpath.");
  }
}