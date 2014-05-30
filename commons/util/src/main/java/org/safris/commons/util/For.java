package org.safris.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class For {
  public static interface Filter<T> {
    public boolean filter(final T value, final Object ... args);
  }

  public static interface Recurser<T> {
    public T[] recurse(final T[] value);
  }

  public static <I>I[] lfor(final Filter<I> filter, final I[] objects) {
    if (filter == null)
      throw new NullPointerException("filter == null");
    
    if (objects == null || objects.length == 0)
      return null;
    
    final List<I> list = new ArrayList<I>(objects.length);
    for (int i = 0; i < objects.length; i++) {
      if (filter.filter(objects[i]))
        list.add(objects[i]);
    }

    return list.size() != 0 ? list.toArray((I[])Array.newInstance(objects.getClass().getComponentType(), list.size())) : null;
  }

  public static <I>I[] rfor(final I[] objects, final Filter<I> filter, final Object ... args) {
    if (filter == null)
      throw new NullPointerException("filter == null");
    
    if (objects == null || objects.length == 0)
      return null;
    
    final Object[][] out = new Object[1][];
    return For.<I>rfor(objects, 0, 0, out, filter, args) ? (I[])out[0] : null;
  }

  public static <I>I[] rfor(final I[] objects, final Recurser<I> recurser, final Filter<I> filter, final Object ... args) {
    if (recurser == null)
      throw new NullPointerException("recurser == null");
    
    if (filter == null)
      throw new NullPointerException("filter == null");
    
    if (objects == null || objects.length == 0)
      return null;
    
    final Object[][] out = new Object[1][];
    return For.<I>rfor(objects, recurser, 0, 0, out, filter, args) ? (I[])out[0] : null;
  }

  private static <I>boolean rfor(final I[] objects, int i, final int depth, final Object[][] array, final Filter<I> filter, final Object ... args) {
    boolean value;
    while (!(value = filter.filter(objects[i++], args)) && i < objects.length);
    
    boolean notNull = true;
    if (i < objects.length)
      notNull = For.<I>rfor(objects, i, value ? depth + 1 : depth, array, filter, args);
    else if (value || depth > 0)
      array[0] = (Object[])Array.newInstance(objects.getClass().getComponentType(), value ? depth + 1 : depth);
    else
      return false;

    if (notNull && value)
      array[0][depth] = objects[i - 1];

    return notNull;
  }

  private static <I>boolean rfor(final I[] objects, final Recurser<I> recurser, int i, final int depth, final Object[][] array, final Filter<I> filter, final Object ... args) {
    boolean value;
    while (!(value = filter.filter(objects[i++], args)) && i < objects.length);
    
    boolean notNull = true;
    if (i < objects.length)
      notNull = For.<I>rfor(objects, i, value ? depth + 1 : depth, array, filter, args);
    else {
      final I[] next = recurser != null ? recurser.recurse(objects) : null;
      if (next != null)
        notNull = For.<I>rfor(next, 0, value ? depth + 1 : depth, array, filter, args);
      else if (value || depth > 0)
        array[0] = (Object[])Array.newInstance(objects.getClass().getComponentType(), value ? depth + 1 : depth);
      else
        return false;
    }

    if (notNull && value)
      array[0][depth] = objects[i - 1];

    return notNull;
  }

  private For() {
  }
}