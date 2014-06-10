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

package org.safris.commons.lang;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.safris.commons.lang.reflect.Classes;

public final class ToStrings {
  private static String spaces(final int size) {
    final char[] chars = new char[size];
    Arrays.fill(chars, ' ');
    return String.valueOf(chars);
  }
  
  public static String toString(final Object obj) {
    return obj != null ? toString(obj, 1) : null;
  }
  
  private static String toString(final Object obj, final int depth) {
    if (obj == null)
      return null;
    
    final Field[] fields = Classes.getDeclaredFieldsDeep(obj.getClass());
    final String pad = spaces(depth);
    final String pad2 = spaces(depth + 1);
    String string = obj.getClass().getSimpleName() + " [@" + Integer.toHexString(System.identityHashCode(obj)) + "] {\n";
    try {
      for (final Field field : fields) {
        if (field.isSynthetic())
          continue;
        
        string += pad + field.getName() + ": ";
        field.setAccessible(true);
        if (boolean.class == field.getType())
          string += Boolean.toString(field.getBoolean(obj));
        else if (byte.class == field.getType())
          string += "0x" + Integer.toHexString(field.getByte(obj));
        else if (char.class == field.getType())
          string += "'" + String.valueOf(field.getChar(obj)) + "'";
        else if (short.class == field.getType())
          string += "(short)" + String.valueOf(field.getShort(obj));
        else if (int.class == field.getType())
          string += String.valueOf(field.getInt(obj));
        else if (long.class == field.getType())
          string += String.valueOf(field.getLong(obj)) + "l";
        else if (float.class == field.getType())
          string += String.valueOf(field.getFloat(obj)) + "f";
        else if (double.class == field.getType())
          string += String.valueOf(field.getDouble(obj)) + "d";
        else {
          final Object fieldObject = field.get(obj);
          if (fieldObject == obj)
            return "";
            
          if (fieldObject == null)
            string += "null";
          else if (String.class == fieldObject.getClass())
            string += "\"" + fieldObject + "\"";
          else if (fieldObject.getClass().isArray()) {
            int length = Array.getLength(fieldObject);  
            String arrayString = "";
            for (int i = 0; i < length; i++) {
              final Object member = Array.get(fieldObject, i);
              arrayString += pad2 + ", " + (member != null ? toString(member, depth + 1) : "null");
            }
            
            if (arrayString.length() >= 2)
              arrayString = arrayString.substring(2) + "\n";
            
            string += "[\n" + arrayString + pad + "]";
          }
          else if (Enum.class.isInstance(fieldObject))
            string += field.getType().getSimpleName() + "." + fieldObject;
          else if (Class.class == fieldObject.getClass())
            string += ((Class<?>)fieldObject).getName();
          else
            string += toString(fieldObject, depth + 1);
        }
        
        string += ",\n";
      }
      
      return string.substring(0, string.length() - 2) + "\n}";
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
  
  private ToStrings() {
  }
}