/*  Copyright Safris Software 2014
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

package org.safris.commons.measure;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.safris.commons.measure.Dimension.Unit;
import org.safris.commons.util.Combinations;

public class MeasurementTest {
  private static Dimension.Unit[] getUnits(final Class<?> unitClass) throws Exception {
    final List<Dimension.Unit> units = new ArrayList<Dimension.Unit>();
    final Field[] fields = unitClass.getDeclaredFields();
    for (final Field field : fields) {
      field.setAccessible(true);
      if (Modifier.isStatic(field.getModifiers()) && Dimension.Unit.class.isAssignableFrom(field.getType()))
        units.add((Dimension.Unit)field.get(null));
    }

    return units.toArray(new Dimension.Unit[units.size()]);
  }

  private static Object[] toArgs(final Object arg, final Object[] units) {
    final Object[] args = new Object[units.length + 1];
    args[0] = arg;
    System.arraycopy(units, 0, args, 1, units.length);
    return args;
  }

  private static final Map<Class<?>,Method> unitFactoryMethods = new HashMap<Class<?>,Method>();

  private static Method getFactoryMethod(final Class<?> unitClass) {
    if (unitClass.getDeclaringClass() != Dimension.Unit.class)
      return null;

    Method factoryMethod = unitFactoryMethods.get(unitClass);
    if (factoryMethod != null)
      return factoryMethod;

    synchronized (unitFactoryMethods) {
      if (factoryMethod != null)
        return factoryMethod;

      for (final Method method : unitClass.getDeclaringClass().getDeclaredMethods()) {
        if (Modifier.isStatic(method.getModifiers()) && method.getReturnType() == unitClass) {
          unitFactoryMethods.put(unitClass, factoryMethod = method);
          break;
        }
      }
    }

    return factoryMethod;
  }

  private static void assertMeasurementUnits(final Class<?> dimensionClass, final Class<?> ... unitClasses) throws Exception {
    final Constructor<?> constructor = dimensionClass.getConstructors()[0];
    final Dimension.Unit[][] allUnits = new Dimension.Unit[unitClasses.length][];
    int i = 0;
    for (final Class<?> unitClass : unitClasses)
      allUnits[i++] = getUnits(unitClass);

    final double value = 100;
    Dimension.Unit[][] combinations = Combinations.<Dimension.Unit>combine(allUnits);
    for (final Dimension.Unit[] from : combinations) {
      final Class<?> unitType = constructor.getParameterTypes()[1];
      final Method factoryMethod = getFactoryMethod(unitType);
      final Object[] fromArgs = factoryMethod != null ? new Object[] {factoryMethod.invoke(null, from)} : from;
      final Object measurement = constructor.newInstance(toArgs(value, fromArgs));
      for (final Dimension.Unit[] to : combinations) {
        final Method[] methods = measurement.getClass().getMethods();
        for (final Method method : methods) {
          if ("value".equals(method.getName())) {
            final Object[] toArgs = factoryMethod != null ? new Object[] {factoryMethod.invoke(null, to)} : to;
            final double scalar = (double)method.invoke(measurement, toArgs);
            final Object measurement2 = constructor.newInstance(toArgs(scalar, toArgs));
            final double back = (double)method.invoke(measurement2, fromArgs);
            System.out.print(measurement + " = " + measurement2 + " = " + constructor.newInstance(toArgs(back, fromArgs)));
            assertEquals(value, back, 0.000001);
            System.out.println(" [OK]");
          }
        }
      }
    }
  }

  @Test
  public void testUnits() throws Exception {
    assertMeasurementUnits(Distance.class, Distance.Unit.class);
    assertMeasurementUnits(Elevation.class, Elevation.Unit.class);
    assertMeasurementUnits(Time.class, Time.Unit.class);
    new Speed(100, Unit.ratio(Distance.Unit.KM, Time.Unit.HR)).value(Unit.ratio(Distance.Unit.KM, Time.Unit.HR));
    assertMeasurementUnits(Speed.class, Distance.Unit.class, Time.Unit.class);
  }
}