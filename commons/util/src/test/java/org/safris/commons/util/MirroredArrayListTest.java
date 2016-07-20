package org.safris.commons.util;

import org.junit.Assert;
import org.junit.Test;

public class MirroredArrayListTest {
  @Test
  public void test() {
    final MirroredArrayList<String,Integer> list = new MirroredArrayList<String,Integer>(new MirroredArrayList.Mirror<String,Integer>() {
      @Override
      public Integer reflect(final String value) {
        return Integer.valueOf(value);
      }
    }, new MirroredArrayList.Mirror<Integer,String>() {
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