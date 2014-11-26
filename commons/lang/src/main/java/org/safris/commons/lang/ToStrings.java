/* Copyright (c) 2014 Seva Safris
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

import java.io.File;
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
    final String pad = spaces(depth * 2);
    final String pad2 = spaces((depth + 1) * 2);
    String string = obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj)) + " {\n";
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
          else if (File.class == fieldObject.getClass())
            string += ((File)fieldObject).getAbsolutePath();
          else
            string += toString(fieldObject, depth + 1);
        }

        string += ",\n";
      }

      return string.substring(0, string.length() - 2) + "\n" + spaces((depth - 1) * 2) + "}";
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private ToStrings() {
  }
}