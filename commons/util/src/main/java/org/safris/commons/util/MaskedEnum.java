package org.safris.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MaskedEnum {
  public static int toggle(int mask, final MaskedEnum ... enm) {
    for (MaskedEnum e : enm)
      mask ^= 1 << e.ordinal;

    return mask;
  }

  public static int set(int mask, final MaskedEnum ... enm) {
    for (MaskedEnum e : enm)
      mask |= 1 << e.ordinal;

    return mask;
  }

  public static int unset(int mask, final MaskedEnum ... enm) {
    for (MaskedEnum e : enm)
      mask &= 1 << e.ordinal;

    return mask;
  }

  public static boolean check(final int mask, final int ordinal) {
    return (mask & (1 << ordinal)) != 0;
  }

  public static boolean check(final int mask, final MaskedEnum enm) {
    return check(mask, enm.ordinal);
  }
  
  @SuppressWarnings("unchecked")
  protected static <T extends MaskedEnum>T valueOf(final Class<T> callingClass, final int ordinal) {
    return ((Map<Integer,T>)enums.get(callingClass)).get(ordinal);
  }

  @SuppressWarnings("unchecked")
  protected static <T extends MaskedEnum>T[] toArray(final Class<T> callingClass, final int mask) {
    final Map<Integer,T> map = (Map<Integer,T>)enums.get(callingClass);
    final List<T> list = new ArrayList<T>();
    for (int i = 0; i < map.size(); i++)
      if ((mask & (1 << i)) != 0)
        list.add((T)map.get(i));

    return (T[])list.toArray((T[])Array.newInstance(callingClass, list.size()));
  }

  protected static final Map<Class<?>,Map<Integer,MaskedEnum>> enums = new HashMap<Class<?>,Map<Integer,MaskedEnum>>();

  public final int ordinal;

  protected MaskedEnum(final int ordinal) {
    this.ordinal = ordinal;
    Map<Integer,MaskedEnum> map = enums.get(getClass());
    if (map == null)
      enums.put(getClass(), map = new HashMap<Integer,MaskedEnum>());

    final MaskedEnum existing = map.get(ordinal);
    if (existing != null)
      throw new IllegalArgumentException("Ordinal [" + ordinal + "] already used by " + existing);

    map.put(ordinal, this);
  }

  public int toMask() {
    return set(0, this);
  }
}