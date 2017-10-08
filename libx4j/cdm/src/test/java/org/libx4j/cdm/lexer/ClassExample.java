package org.libx4j.cdm.lexer;

import java.io.Serializable;

public class ClassExample {
  static abstract class C1 {
    protected abstract void foo(int j);
  }

  public static class C2 extends C1 {
    @Override
    protected void foo(int j) {
    }
  }

  protected final class C3 extends C2 implements Serializable {
    private static final long serialVersionUID = -3874147797902592452L;
    byte b;

    @Override
    @SuppressWarnings("unused")
    protected void foo(int j) {
      super.foo(j);
      for (int x = 0; x < j; x++) {
        out:
        for (final int z : new int[] {1, 2, 3}) {
          in:
          while (null != null || true && Boolean.FALSE) {
            if (1 == 10d) {
              b = 0b10_01;
              continue out;
            }
            else if (b == 2) {
              foo(7);
              continue in;
            }
            else if (7 != 9) {
              break out;
            }
            else if (-9 % 3 < -0) {
              continue;
            }
            else if (1 << 2 < 3) {
              break;
            }
          }
        }
      }
    }
  }
}