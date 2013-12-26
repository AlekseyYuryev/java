/*  Copyright Safris Software 2013
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

public final class Primitives {
  public static Object cast(final Object primitive, final Class<?> cls) {
    if (primitive == null)
      return null;
    
    if (cls.isInstance(primitive))
      return primitive;

    if (!cls.isPrimitive())
      throw new IllegalArgumentException("cls is not of a primitive type");

    if (primitive instanceof Number) {
      if (Byte.class.isAssignableFrom(cls))
        return new Byte(((Number)primitive).byteValue());

      if (Short.class.isAssignableFrom(cls))
        return new Short(((Number)primitive).shortValue());

      if (Integer.class.isAssignableFrom(cls))
        return new Integer(((Number)primitive).intValue());

      if (Long.class.isAssignableFrom(cls))
        return new Long(((Number)primitive).longValue());

      if (Float.class.isAssignableFrom(cls))
        return new Float(((Number)primitive).floatValue());

      if (Double.class.isAssignableFrom(cls))
        return new Double(((Number)primitive).doubleValue());

      if (Boolean.class.isAssignableFrom(cls))
        return new Boolean(((Number)primitive).intValue() != 0);

      if (Character.class.isAssignableFrom(cls))
        return new Character((char)((Number)primitive).intValue());
    }
    else if (primitive instanceof Boolean) {
      if (Byte.class.isAssignableFrom(cls))
        return new Byte((byte)((Boolean)primitive ? 1 : 0));

      if (Short.class.isAssignableFrom(cls))
        return new Short((short)((Boolean)primitive ? 1 : 0));

      if (Integer.class.isAssignableFrom(cls))
        return new Integer((int)((Boolean)primitive ? 1 : 0));

      if (Long.class.isAssignableFrom(cls))
        return new Long((long)((Boolean)primitive ? 1 : 0));

      if (Float.class.isAssignableFrom(cls))
        return new Float((float)((Boolean)primitive ? 1 : 0));

      if (Double.class.isAssignableFrom(cls))
        return new Double((double)((Boolean)primitive ? 1 : 0));

      if (Character.class.isAssignableFrom(cls))
        return new Character((char)((Boolean)primitive ? 1 : 0));
    }
    else if (primitive instanceof Character) {
      if (Byte.class.isAssignableFrom(cls))
        return new Byte((byte)((Character)primitive).charValue());

      if (Short.class.isAssignableFrom(cls))
        return new Short((short)((Character)primitive).charValue());

      if (Integer.class.isAssignableFrom(cls))
        return new Integer((int)((Character)primitive).charValue());

      if (Long.class.isAssignableFrom(cls))
        return new Long((long)((Character)primitive).charValue());

      if (Float.class.isAssignableFrom(cls))
        return new Float((float)((Character)primitive).charValue());

      if (Double.class.isAssignableFrom(cls))
        return new Double((double)((Character)primitive).charValue());

      if (Boolean.class.isAssignableFrom(cls))
        return new Boolean((int)((Character)primitive).charValue() != 0);
    }
    
    throw new IllegalArgumentException("Unknown cast from " + primitive.getClass().getName() + " to " + cls.getName());
  }

  private Primitives() {
  }
}