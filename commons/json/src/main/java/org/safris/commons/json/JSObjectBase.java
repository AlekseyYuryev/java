/* Copyright (c) 2015 Seva Safris
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

package org.safris.commons.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.json.decoder.BooleanDecoder;
import org.safris.commons.json.decoder.NumberDecoder;
import org.safris.commons.json.decoder.ObjectDecoder;
import org.safris.commons.json.decoder.StringDecoder;
import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;
import org.safris.commons.util.Collections;

public abstract class JSObjectBase {
  private static final BooleanDecoder booleanDecoder = new BooleanDecoder();
  private static final NumberDecoder numberDecoder = new NumberDecoder();
  private static final StringDecoder stringDecoder = new StringDecoder();
  private static final ObjectDecoder objectDecoder = new ObjectDecoder();

  private static final Map<String,Class<? extends JSObject>> bindings = new HashMap<String,Class<? extends JSObject>>();

  static {
    try {
      PackageLoader.getSystemPackageLoader().loadPackage("json");
    }
    catch (final PackageNotFoundException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  protected static void registerBinding(final String name, final Class<? extends JSObject> bindingClass) {
    bindings.put(name, bindingClass);
  }

  protected static String pad(final int depth) {
    final StringBuilder out = new StringBuilder();
    for (int i = 0; i < depth; i++)
      out.append("  ");

    return out.toString();
  }

  protected static char next(final java.io.InputStream in) throws java.io.IOException {
    int ch;
    while (true) {
      ch = in.read();
      if (ch == -1)
        throw new java.io.IOException("EOS");

      if (ch != ' ' && ch != '\t' && ch != '\n' && ch != '\r')
        return (char)ch;
    }
  }

  protected static char nextAny(final java.io.InputStream in) throws java.io.IOException {
    int ch;
    while (true) {
      ch = in.read();
      if (ch == -1)
        throw new java.io.IOException("EOS");

      return (char)ch;
    }
  }

  protected static boolean isNull(char ch, final InputStream in) throws IOException {
    return ch == 'n' && next(in) == 'u' && next(in) == 'l' && next(in) == 'l';
  }

  protected static String format(final Number number) {
    return number == null ? null : (number.intValue() == number.doubleValue() ? String.valueOf(number.intValue()) : String.valueOf(number.doubleValue()));
  }

  protected static <T> String tokenize(final Collection<T> value, final int depth) {
    if (value == null)
      return "null";

    if (value.size() == 0)
      return "[]";

    final StringBuilder out = new StringBuilder();
    for (final T part : value)
      out.append(", ").append(part == null ? "null" : part instanceof JSObject ? ((JSObject)part).encode(depth) : "\"" + part + "\"");

    return "[" + out.substring(2) + "]";
  }

  protected static JSObject decode(final InputStream in, char ch, final JSObject jsObject) throws DecodeException, IOException {
    boolean hasOpenBrace = false;
    boolean hasStartQuote = false;
    final StringBuilder out = new StringBuilder();
    while (true) {
      if (ch == '{') {
        if (hasOpenBrace)
          throw new DecodeException("Malformed JSON", jsObject);

        hasOpenBrace = true;
      }
      else {
        if (!hasOpenBrace)
          throw new DecodeException("Malformed JSON", jsObject);

        if (ch == '"') {
          if (!hasStartQuote) {
            hasStartQuote = true;
          }
          else {
            hasStartQuote = false;
            ch = next(in);
            if (ch != ':')
              throw new DecodeException("Malformed JSON", jsObject);

            try {
              // Special case for parsing the container object
              if (jsObject == null) {
                final Class<? extends JSObject> clazz = bindings.get(out.toString());
                if (clazz == null)
                  throw new DecodeException("Unknown container name: " + out, jsObject);

                return decode(in, next(in), clazz.newInstance());
              }

              final Binding member = jsObject.lookupBinding(out.toString());
              if (member == null)
                throw new DecodeException("Unknown object name: " + out, jsObject);

              out.setLength(0);
              ch = next(in);
              final boolean isArray = ch == '[';
              final Object value;
              if (JSObject.class.isAssignableFrom(member.type)) {
                value = isArray ? Collections.asCollection(ArrayList.class, objectDecoder.recurse(in, member.type, 0)) : decode(in, ch, (JSObject)member.type.newInstance());
              }
              else if (member.type == String.class) {
                value = isArray ? Collections.asCollection(ArrayList.class, stringDecoder.recurse(in, 0)) : stringDecoder.decode(in, ch);
                final Pattern pattern = member.field.getAnnotation(Pattern.class);
                if (pattern != null && value != null && !((String)value).matches(pattern.value()))
                  throw new DecodeException("\"" + member.name + "\" does not match pattern \"" + pattern.value() + "\": " + value + "\"", jsObject);
              }
              else if (member.type == Boolean.class) {
                value = isArray ? Collections.asCollection(ArrayList.class, booleanDecoder.recurse(in, 0)) : booleanDecoder.decode(in, ch);
              }
              else if (member.type == Number.class) {
                value = isArray ? Collections.asCollection(ArrayList.class, numberDecoder.recurse(in, 0)) : numberDecoder.decode(in, ch);
              }
              else {
                throw new UnsupportedOperationException("Unexpected type: " + member.type);
              }

              final NotNull notNull = member.field.getAnnotation(NotNull.class);
              if (notNull != null && value == null)
                throw new DecodeException("\"" + member.name + "\" cannot be null", jsObject);

              member.field.set(jsObject, value);
            }
            catch (final ReflectiveOperationException e) {
              throw new Error(e);
            }
          }
        }
        else {
          if (ch == '}') {
            try {
              final Field[] fields = jsObject.getClass().getDeclaredFields();
              for (final Field field : fields) {
                if (field.getName().endsWith("WasSet"))
                  continue;

                field.setAccessible(true);
                if (field.get(jsObject) == null) {
                  final Field wasSetField = jsObject.getClass().getDeclaredField(field.getName() + "WasSet");
                  wasSetField.setAccessible(true);
                  if (!wasSetField.getBoolean(jsObject))
                    throw new DecodeException("\"" + field.getAnnotation(Name.class).value() + "\" is missing", jsObject);

                  if (field.getAnnotation(NotNull.class) != null)
                    throw new DecodeException("\"" + field.getAnnotation(Name.class).value() + "\" cannot be null", jsObject);
                }
              }
            }
            catch (final ReflectiveOperationException e) {
              throw new Error(e);
            }

            return jsObject;
          }

          if (ch != ',') {
            out.append(ch);
          }
        }
      }

      ch = next(in);
    }
  }
}