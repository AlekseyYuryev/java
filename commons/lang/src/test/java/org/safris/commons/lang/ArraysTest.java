package org.safris.commons.lang;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class ArraysTest extends LoggableTest {
  @Test
  public void testFilter() {
    Assert.assertArrayEquals(new String[] {"ONE", "TWO", "THREE"}, Arrays.<String>filter(new Arrays.Filter<String>() {
      @Override
      public String filter(final String value) {
        return value.toUpperCase();
      }
    }, new String[] {"one", "two", "three"}));

    Assert.assertArrayEquals(new String[] {}, Arrays.<String>filter(new Arrays.Filter<String>() {
      @Override
      public String filter(final String value) {
        return value.toUpperCase();
      }
    }, new String[] {}));
  }
}