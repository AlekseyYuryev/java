/* Copyright (c) 2016 Seva Safris
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