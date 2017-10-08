package org.libx4j.cdm.lexer;

public class ConditionExample {
  @SuppressWarnings({"cast", "unused"})
  public boolean condition() {
    if ((7 & 4) == (8 & 4)) {
      System.exit(7);
    }
    else if (this instanceof ConditionExample) {
      int i = 57;
    }
    else {
      condition();
    }

    return false;
  }

  public native void s();
}