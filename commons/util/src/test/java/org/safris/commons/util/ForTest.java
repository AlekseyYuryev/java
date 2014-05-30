package org.safris.commons.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ForTest {
  private static final Integer[] values1 = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 3, 4, 0, 0, 0, 5, 0, 0, 6, 0, 0, 7, 0, 0, 8};
  private static final Integer[] values2 = {0, 0, 0, 0, 0, 0, 0, 0};
  private static final For.Filter<Integer> filter = new For.Filter<Integer>() {
    public boolean filter(final Integer value, final Object ... args) {
      return 1 == value || (3 < value && value < 7) || value == 8;
    }
  };
  private Integer[] array = null;

  @Test
  public void testLFor() {
    System.gc();

    long start = System.currentTimeMillis();
    long mem = Runtime.getRuntime().freeMemory();
    for (int i = 0; i < 1000000; i++)
      array = For.<Integer>lfor(filter, values1);

    System.out.println("lfor: " + (System.currentTimeMillis() - start) + "ms " + (mem - Runtime.getRuntime().freeMemory()) + " bytes");
    assertArrayEquals(new Integer[] {1, 4, 5, 6, 8}, array);
    
    assertNull(For.<Integer>lfor(filter, values2));
  }
  
  @Test
  public void testRFor() {
    System.gc();

    final long start = System.currentTimeMillis();
    final long mem = Runtime.getRuntime().freeMemory();
    for (int i = 0; i < 1000000; i++)
      array = For.<Integer>rfor(values1, filter);

    System.out.println("rfor: " + (System.currentTimeMillis() - start) + "ms " + (mem - Runtime.getRuntime().freeMemory()) + " bytes");
    assertArrayEquals(new Integer[] {1, 4, 5, 6, 8}, array);

    array = For.<Integer>rfor(values2, filter);
    assertNull(array);
  }
}