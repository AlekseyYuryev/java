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

package org.safris.commons.util;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.safris.commons.test.LoggableTest;

public class MirroredListTest extends LoggableTest {
  @Test
  public void test() {
    final MirroredList<String,Integer> list = new MirroredList<String,Integer>(ArrayList.class, new MirroredList.Mirror<String,Integer>() {
      @Override
      public Integer reflect(final String value) {
        return Integer.valueOf(value);
      }
    }, new MirroredList.Mirror<Integer,String>() {
      @Override
      public String reflect(final Integer value) {
        return String.valueOf(value);
      }
    });

    list.add("1");
    Assert.assertTrue(list.getMirror().contains(1));

    list.getMirror().add(2);
    Assert.assertTrue(list.contains("2"));

    list.remove("1");
    Assert.assertTrue(!list.getMirror().contains(1));

    list.getMirror().remove((Object)2);
    Assert.assertTrue(!list.contains("2"));
  }
}