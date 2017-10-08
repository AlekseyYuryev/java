package org.libx4j.cdm.lexer;

public class LoopExample {
  public static void forLoop() {
    for (int i = 0; i < 100; i++) {
      System.out.println(i);
    }
  }

  public static void forEachLoop() {
    final char[] chars = "hello".toCharArray();
    for (final char ch : chars) {
      System.out.println(ch);
    }
  }

  @SuppressWarnings("unused")
  public static synchronized void whileLoop() {
    while (true || 8 == 7) {
      System.out.println(Thread.currentThread().getName());
    }
  }

  @SuppressWarnings("unused")
  public static void doWhileLoop() {
    do {
      System.out.println(Thread.currentThread().getName());
    }
    while (true || 8 == 7);
  }
}