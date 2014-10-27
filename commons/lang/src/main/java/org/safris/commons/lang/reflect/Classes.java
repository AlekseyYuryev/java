/* Copyright (c) 2006 Seva Safris
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

package org.safris.commons.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.safris.commons.util.For;

import sun.reflect.Reflection;

public final class Classes {
  private static final Map<Class<?>,Map<String,Field>> classToFields = new HashMap<Class<?>,Map<String,Field>>();

  public static Class<?> getCallerClass(final int stackDepth) {
    return Reflection.getCallerClass(stackDepth);
  }

  public static Field getField(final Class<?> cls, final String fieldName) {
    return Classes.getField(cls, fieldName, false);
  }

  public static Field getDeclaredField(final Class<?> cls, final String fieldName) {
    return Classes.getField(cls, fieldName, true);
  }

  private static Field getField(final Class<?> cls, final String fieldName, final boolean declared) {
    if (cls == null)
      throw new NullPointerException("cls == null");

    if (fieldName == null)
      throw new NullPointerException("fieldName == null");

    Map<String,Field> fieldMap = classToFields.get(cls);
    if (fieldMap != null)
      return checkAccessField(fieldMap.get(cls), declared);

    synchronized (classToFields) {
      fieldMap = classToFields.get(cls);
      if (fieldMap != null)
        return checkAccessField(fieldMap.get(cls), declared);

      final Field[] fields = declared ? cls.getDeclaredFields() : cls.getFields();
      classToFields.put(cls, fieldMap = new HashMap<String,Field>());
      for (final Field field : fields) {
        field.setAccessible(true);
        fieldMap.put(field.getName(), field);
      }

      return checkAccessField(fieldMap.get(cls), declared);
    }
  }

  public static Field getDeclaredFieldDeep(Class<?> clazz, final String name) {
    Field field;
    do
      field = Classes.getDeclaredField(clazz, name);
    while (field == null && (clazz = clazz.getSuperclass()) != null);
    return field;
  }

  private static Field checkAccessField(final Field field, final boolean declared) {
    return declared || Modifier.isPublic(field.getModifiers()) ? field : null;
  }

  public static Class<?> forName(final String className, final boolean initialize) {
    if (className == null || className.length() == 0)
      return null;

    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    try {
      return Class.forName(className, initialize, classLoader);
    }
    catch (final ClassNotFoundException e) {
    }

    classLoader = Thread.currentThread().getContextClassLoader();
    try {
      return Class.forName(className, initialize, classLoader);
    }
    catch (final ClassNotFoundException e) {
    }

    classLoader = Classes.getCallerClass(2).getClassLoader();
    try {
      return Class.forName(className, initialize, classLoader);
    }
    catch (final ClassNotFoundException e) {
    }

    return null;
  }

  public static Class<?> forName(final String className) {
    return Classes.forName(className, false);
  }

  public static <T extends Annotation>T getDeclaredAnnotation(final Class<?> clazz, final Class<T> annotationType) {
    for (final Annotation annotation : clazz.getDeclaredAnnotations())
      if (annotation.annotationType() == annotationType)
        return (T)annotation;

    return null;
  }

  private static final For.Filter<Field> fieldWithAnnotationFilter = new For.Filter<Field>() {
    public boolean filter(final Field value, final Object ... args) {
      return value.getAnnotation((Class)args[0]) != null;
    }
  };

  private static final For.Recurser<Field> fieldWithAnnotationRecurser = new For.Recurser<Field>() {
    public Field[] recurse(final Field[] value) {
      final Class<?> clazz = value[0].getDeclaringClass().getSuperclass();
      return clazz != null ? clazz.getDeclaredFields() : null;
    }
  };

  /**
   * Find declared Field(s) in the clazz that have an annotation annotationType, executing a comparator callback for content matching.
   *
   * The comparator compareTo method may return: 0 if there is a match, -1 if there if no match, and 1 if there is a match & to return Field retuls after this
   * match.
   *
   * @param clazz
   * @param annotationType
   * @param comparable
   * @return
   */
  public static <T extends Annotation>Field[] getDeclaredFieldsWithAnnotation(final Class<?> clazz, final Class<T> annotationType) {
    return For.<Field>rfor(clazz.getDeclaredFields(), fieldWithAnnotationFilter, annotationType);
  }

  public static <T extends Annotation>Field[] getDeclaredFieldsWithAnnotationDeep(Class<?> clazz, final Class<T> annotationType) {
    return For.<Field>rfor(clazz.getDeclaredFields(), fieldWithAnnotationRecurser, fieldWithAnnotationFilter, annotationType);
  }

  public static Field[] getFieldsDeep(Class<?> clazz) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    final List<Field> fields = new ArrayList<Field>();
    do
      fields.addAll(Arrays.<Field>asList(clazz.getFields()));
    while ((clazz = clazz.getSuperclass()) != null);
    return fields.toArray(new Field[fields.size()]);
  }

  public static Field[] getDeclaredFieldsDeep(Class<?> clazz) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    final List<Field> fields = new ArrayList<Field>();
    do
      fields.addAll(Arrays.<Field>asList(clazz.getDeclaredFields()));
    while ((clazz = clazz.getSuperclass()) != null);
    return fields.toArray(new Field[fields.size()]);
  }

  public static Method getDeclaredMethod(final Class<?> clazz, final String name, final Class<?> ... parameters) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    if (name == null)
      throw new NullPointerException("name == null");

    final Method[] methods = clazz.getDeclaredMethods();
    for (final Method method : methods)
      if (method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), parameters))
        return method;

    return null;
  }

  public static Method getDeclaredMethodDeep(Class<?> clazz, final String name, final Class<?> ... parameters) {
    Method method;
    do
      method = getDeclaredMethod(clazz, name, parameters);
    while (method == null && (clazz = clazz.getSuperclass()) != null);
    return method;
  }

  public static Class<?> getGreatestCommonSuperclass(final Class<?> ... classes) {
    if (classes == null || classes.length == 0)
      return null;

    if (classes.length == 1)
      return classes[0];

    Class<?> gcc = getGreatestCommonSuperclass(classes[0], classes[1]);
    for (int i = 2; i < classes.length && gcc != null; i++)
      gcc = getGreatestCommonSuperclass(gcc, classes[i]);

    return gcc;
  }

  private static Class<?> getGreatestCommonSuperclass(final Class<?> class1, final Class<?> class2) {
    Class<?> super1 = class1;
    do {
      Class<?> super2 = class2;
      do
        if (super1.isAssignableFrom(super2))
          return super1;
      while ((super2 = super2.getSuperclass()) != null);
    }
    while ((super1 = super1.getSuperclass()) != null);
    return null;
  }

  private Classes() {
  }
}