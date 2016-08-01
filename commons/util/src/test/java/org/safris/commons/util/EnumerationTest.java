package org.safris.commons.util;

import java.util.Enumeration;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class EnumerationTest extends LoggableTest  {
  private final Enumeration<Integer> enumeration = new Enumeration<Integer>() {
    private int position = 0;

    @Override
    public boolean hasMoreElements() {
      return position < 10;
    }

    @Override
    public Integer nextElement() {
      return position++;
    }
  };

  @Test
  public void testToArray() {
    final Integer[] array = Enumerations.toArray(Integer.class, enumeration);
    Assert.assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
  }
}