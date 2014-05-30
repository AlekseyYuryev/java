/*  Copyright Safris Software 2008
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

package org.safris.commons.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

import org.safris.commons.util.Collections;

public final class Systems {
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