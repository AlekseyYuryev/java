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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.util.For;

public final class Classes {
  private static final Map<Class<?>,Map<String,Field>> classToFields = new HashMap<Class<?>,Map<String,Field>>();

  public static Type[] getGenericSuperclasses(final Class<?> cls) {
    return ((ParameterizedType)cls.getGenericSuperclass()).getActualTypeArguments();
  }

  private static Field checkAccessField(final Field field, final boolean declared) {
    return declared || Modifier.isPublic(field.getModifiers()) ? field : null;
  }

  private static Field getField(final Class<?> cls, final String fieldName, final boolean declared) {
    if (cls == null)
      throw new NullPointerException("cls == null");

    if (fieldName == null)
      throw new NullPointerException("fieldName == null");

    Map<String,Field> fieldMap = classToFields.get(cls);
    if (fieldMap != null)
      return checkAccessField(fieldMap.get(fieldName), declared);

    synchronized (classToFields) {
      if ((fieldMap = classToFields.get(cls)) != null)
        return checkAccessField(fieldMap.get(cls), declared);

      final Field[] fields = declared ? cls.getDeclaredFields() : cls.getFields();
      classToFields.put(cls, fieldMap = new HashMap<String,Field>());
      for (final Field field : fields)
        fieldMap.put(field.getName(), field);

      return checkAccessField(fieldMap.get(fieldName), declared);
    }
  }

  public static Field getField(final Class<?> cls, final String fieldName) {
    return Classes.getField(cls, fieldName, false);
  }

  public static Field getDeclaredField(final Class<?> cls, final String fieldName) {
    return Classes.getField(cls, fieldName, true);
  }

  public static Field getDeclaredFieldDeep(Class<?> clazz, final String name) {
    Field field;
    do
      field = Classes.getDeclaredField(clazz, name);
    while (field == null && (clazz = clazz.getSuperclass()) != null);
    return field;
  }

  public static <T extends Annotation>T getDeclaredAnnotation(final Class<?> clazz, final Class<T> annotationType) {
    for (final Annotation annotation : clazz.getDeclaredAnnotations())
      if (annotation.annotationType() == annotationType)
        return (T)annotation;

    return null;
  }

  private static final For.Recurser<Field,Class<?>> declaredFieldRecurser = new For.Recurser<Field,Class<?>>() {
    public boolean accept(final Field item, final Object ... args) {
      return true;
    }

    public Field[] items(final Class<?> container) {
      return container.getDeclaredFields();
    }

    public Class<?> next(final Class<?> container) {
      return container.getSuperclass();
    }
  };

  private static final For.Recurser<Field,Class<?>> fieldRecurser = new For.Recurser<Field,Class<?>>() {
    public boolean accept(final Field field, final Object ... args) {
      return Modifier.isPublic((field).getModifiers());
    }

    public Field[] items(final Class<?> clazz) {
      return clazz.getDeclaredFields();
    }

    public Class<?> next(final Class<?> clazz) {
      return clazz.getSuperclass();
    }
  };

  private static final For.Filter<Field> declaredFieldWithAnnotationFilter = new For.Filter<Field>() {
    public boolean accept(final Field item, final Object ... args) {
      return item.getAnnotation((Class)args[0]) != null;
    }
  };

  private static final For.Filter<Class<?>> classWithAnnotationFilter = new For.Filter<Class<?>>() {
    public boolean accept(final Class<?> item, final Object ... args) {
      return item.getAnnotation((Class)args[0]) != null;
    }
  };

  private static final For.Recurser<Class<?>,Class<?>> classWithAnnotationRecurser = new For.Recurser<Class<?>,Class<?>>() {
    public boolean accept(final Class<?> item, final Object ... args) {
      return item.getAnnotation((Class)args[0]) != null;
    }

    public Class<?>[] items(final Class<?> container) {
      return container.getDeclaredClasses();
    }

    public Class<?> next(final Class<?> container) {
      return container.getSuperclass();
    }
  };

  /**
   * Find declared Field(s) in the clazz that have an annotation annotationType, executing a comparator callback for content matching.
   *
   * The comparator compareTo method may return: 0 if there is a match, -1 if there if no match, and 1 if there is a match & to return Field result after this
   * match.
   *
   * @param clazz
   * @param annotationType
   * @param comparable
   * @return
   */
  public static <T extends Annotation>Field[] getDeclaredFieldsWithAnnotation(final Class<?> clazz, final Class<T> annotationType) {
    return For.<Field>recursiveOrdered(clazz.getDeclaredFields(), Field.class, declaredFieldWithAnnotationFilter, annotationType);
  }

  public static <T extends Annotation>Field[] getDeclaredFieldsWithAnnotationDeep(Class<?> clazz, final Class<T> annotationType) {
    return For.<Field,Class<?>>recursiveInverted(clazz, clazz.getDeclaredFields(), Field.class, declaredFieldRecurser, annotationType);
  }

  /**
   * Find declared Class(es) in the clazz that have an annotation annotationType, executing a comparator callback for content matching.
   *
   * The comparator compareTo method may return: 0 if there is a match, -1 if there if no match, and 1 if there is a match & to return Class<?> result after this
   * match.
   *
   * @param clazz
   * @param annotationType
   * @param comparable
   * @return
   */
  public static <T extends Annotation>Class<?>[] getDeclaredClassesWithAnnotation(final Class<?> clazz, final Class<T> annotationType) {
    return For.<Class<?>>recursiveOrdered(clazz.getDeclaredClasses(), (Class<Class<?>>)Class.class.getClass(), classWithAnnotationFilter, annotationType);
  }

  public static <T extends Annotation>Class<?>[] getDeclaredClassesWithAnnotationDeep(Class<?> clazz, final Class<T> annotationType) {
    return For.<Class<?>,Class<?>>recursiveInverted(clazz, clazz.getDeclaredClasses(), (Class<Class<?>>)Class.class.getClass(), classWithAnnotationRecurser, annotationType);
  }

  public static Field[] getFieldsDeep(final Class<?> clazz) {
    return For.recursiveInverted(clazz, clazz.getDeclaredFields(), Field.class, fieldRecurser);
  }

  public static Field[] getDeclaredFieldsDeep(final Class<?> clazz) {
    return For.<Field,Class<?>>recursiveInverted(clazz, clazz.getDeclaredFields(), Field.class, declaredFieldRecurser);
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

  public static Class<?> forName(final String className, final boolean initialize, final Class<?> callerClass) {
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

    classLoader = callerClass.getClassLoader();
    try {
      return Class.forName(className, initialize, classLoader);
    }
    catch (final ClassNotFoundException e) {
    }

    return null;
  }

  public static Class<?> forName(final String className, final Class<?> callerClass) {
    return Classes.forName(className, false, callerClass);
  }

  private static Class<?> getGreatestCommonSuperclass(Class<?> class1, final Class<?> class2) {
    do {
      Class<?> super2 = class2;
      do
        if (class1.isAssignableFrom(super2))
          return class1;
      while ((super2 = super2.getSuperclass()) != null);
    }
    while ((class1 = class1.getSuperclass()) != null);
    return null;
  }

  private Classes() {
  }
}