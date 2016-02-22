/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

import org.safris.commons.util.Collections;

public final class Systems {
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static boolean setenv(final String name, final String value) {
    Class<?> processEnvironmentClass;
    Field theEnvironmentField;
    Field theUnmodifiableEnvironmentField;
    Map theEnvironment;
    Map theUnmodifiableEnvironment;
    try {
      processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
      theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
      theEnvironmentField.setAccessible(true);

      theUnmodifiableEnvironmentField = processEnvironmentClass.getDeclaredField("theUnmodifiableEnvironment");
      theUnmodifiableEnvironmentField.setAccessible(true);

      theEnvironment = (Map)theEnvironmentField.get(null);
      theUnmodifiableEnvironment = (Map)theUnmodifiableEnvironmentField.get(null);
    }
    catch (final RuntimeException e) {
      return false;
    }
    catch (final Exception e) {
      return false;
    }

    // Versions: 1.7 06/12/05
    if (theEnvironmentField.getType().isAssignableFrom(processEnvironmentClass)) {
      if (Collections.putUnmodifiableMap(theUnmodifiableEnvironment, name, value)) {
        theEnvironment.put(name, value);
        return true;
      }
    }
    // Versions: 1.5 04/04/05, 1.6 05/11/17
    else {
      final Object variableData = createVariable(name);
      if (variableData == null)
        return false;

      final Object valueData = createValue(value);
      if (valueData == null)
        return false;

      Map unmodifiableMap;
      try {
        final Field unmodifiableMapField = theUnmodifiableEnvironment.getClass().getDeclaredField("m");
        unmodifiableMapField.setAccessible(true);
        unmodifiableMap = (Map)unmodifiableMapField.get(theUnmodifiableEnvironment);
      }
      catch (final RuntimeException e) {
        return false;
      }
      catch (final Exception e) {
        return false;
      }

      if (Collections.putUnmodifiableMap(unmodifiableMap, variableData, valueData)) {
        theEnvironment.put(variableData, valueData);
        return true;
      }
    }

    return false;
  }

  private static Object createVariable(final String name) {
    try {
      final Class<?> clazz = Class.forName("java.lang.ProcessEnvironment$Variable");
      final Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
      constructor.setAccessible(true);
      return constructor.newInstance(name, name.getBytes());
    }
    catch (final RuntimeException e) {
      return null;
    }
    catch (final Exception e) {
      return null;
    }
  }

  private static Object createValue(final String value) {
    try {
      final Class<?> clazz = Class.forName("java.lang.ProcessEnvironment$Value");
      final Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, byte[].class);
      constructor.setAccessible(true);
      return constructor.newInstance(value, value.getBytes());
    }
    catch (final RuntimeException e) {
      return null;
    }
    catch (final Exception e) {
      return null;
    }
  }

  private Systems() {
  }
}