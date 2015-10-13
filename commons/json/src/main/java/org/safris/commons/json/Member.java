package org.safris.commons.json;

import java.lang.reflect.Field;

public class Member {
  public final Field field;
  public final Class<?> type;
  public final boolean array;

  public Member(final Field field, final Class<?> type, final boolean array) {
    field.setAccessible(true);

    this.field = field;
    this.type = type;
    this.array = array;
  }
}