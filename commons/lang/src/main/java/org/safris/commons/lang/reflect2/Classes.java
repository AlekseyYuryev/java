/*  Copyright Safris Software 2006
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Classes {
  /**
   * Find declared Field(s) in the clazz that have an annotation annotationType,
   * executing a comparator callback for content matching.
   * 
   * The comparator compareTo method may return: 0 if there is a match, -1 if there
   * if no match, and 1 if there is a match & to return Field retuls after this match.
   * 
   * @param clazz
   * @param annotationType
   * @param comparable
   * @return
   */
  public static <T extends Annotation> List<Field> getDeclaredFieldsWithAnnotation(final Class<?> clazz, final Class<T> annotationType, final Comparable<T> comparable) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    if (annotationType == null)
      throw new NullPointerException("annotationType == null");

    if (comparable == null)
      throw new NullPointerException("equalable == null");

    final List<Field> fields = new ArrayList<Field>();
    outer:
    for (final Field field : clazz.getDeclaredFields()) {
      for (final Annotation atn : field.getDeclaredAnnotations()) {
        if (annotationType.isInstance(atn)) {
          final int comparison = comparable.compareTo((T)atn);
          if (comparison >= 0) {
            fields.add(field);
            if (comparison == 1)
              return fields;

            continue outer;
          }
        }
      }
    }

    return fields;
  }

  public static <T extends Annotation> List<Field> getDeclaredFieldsWithAnnotationDeep(Class<?> clazz, final Class<T> annotationType, final Comparable<T> comparable) {
    final List<Field> fields = new ArrayList<Field>();
    do
      fields.addAll(getDeclaredFieldsWithAnnotation(clazz, annotationType, comparable));
    while ((clazz = clazz.getSuperclass()) != null);
    return fields;
  }

  public static List<Field> getFieldsDeep(Class<?> clazz) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    final List<Field> fields = new ArrayList<Field>();
    do
      fields.addAll(Arrays.<Field> asList(clazz.getFields()));
    while ((clazz = clazz.getSuperclass()) != null);
    return fields;
  }

  public static List<Field> getDeclaredFieldsDeep(Class<?> clazz) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    final List<Field> fields = new ArrayList<Field>();
    do
      fields.addAll(Arrays.<Field> asList(clazz.getDeclaredFields()));
    while ((clazz = clazz.getSuperclass()) != null);
    return fields;
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

  public static Field getDeclaredField(final Class<?> clazz, final String name) {
    if (clazz == null)
      throw new NullPointerException("clazz == null");

    if (name == null)
      throw new NullPointerException("name == null");

    final Field[] fields = clazz.getDeclaredFields();
    for (final Field field : fields)
      if (field.getName().equals(name))
        return field;

    return null;
  }

  public static Field getDeclaredFieldDeep(Class<?> clazz, final String name) {
    Field field;
    do
      field = getDeclaredField(clazz, name);
    while (field == null && (clazz = clazz.getSuperclass()) != null);
    return field;
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