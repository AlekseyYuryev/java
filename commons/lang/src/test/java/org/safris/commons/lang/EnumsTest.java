package org.safris.commons.lang;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class EnumsTest extends LoggableTest {
  private static enum Fruit {
    APPLE, ORANGE, WATERMELLON
  }

  @Test
  public void testEnums() {
    Assert.assertArrayEquals(new Fruit[] {Fruit.ORANGE, Fruit.WATERMELLON, Fruit.APPLE}, Enums.valueOf(Fruit.class, "ORANGE", "WATERMELLON", "TOMATO", "APPLE"));
    Assert.assertArrayEquals(new Fruit[] {}, Enums.valueOf(Fruit.class, "POTATO", "TOMATO", "CHICKEN"));
    Assert.assertArrayEquals(new Fruit[] {}, Enums.valueOf(Fruit.class));
  }
}