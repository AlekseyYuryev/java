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
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.lang.PackageLoader;
import org.safris.commons.lang.PackageNotFoundException;

public abstract class JSO extends JSOUtil {
  private static final BooleanRecursiveFactory booleanFactory = new BooleanRecursiveFactory();
  private static final NumberRecursiveFactory numberFactory = new NumberRecursiveFactory();
  private static final StringRecursiveFactory stringFactory = new StringRecursiveFactory();
  private static final ObjectRecursiveFactory objectFactory = new ObjectRecursiveFactory();

  private static final Map<String,Class<? extends JSO>> bindings = new HashMap<String,Class<? extends JSO>>();

  static {
    try {
      PackageLoader.getSystemPackageLoader().loadPackage("json");
    }
    catch (final PackageNotFoundException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  protected static void registerBinding(final String name, final Class<? extends JSO> bindingClass) {
    bindings.put(name, bindingClass);
  }

  protected abstract String name();
  protected abstract String encode(final int depth);
  protected abstract Member lookupBinding(final String name);

  private static class Envelope extends JSO {
    private static final Field itemField;

    static {
      try {
        itemField = Envelope.class.getDeclaredField("item");
      }
      catch (final Exception e) {
        throw new ExceptionInInitializerError(e);
      }
    }

    private JSO item;

    protected String name() {
      return null;
    }

    protected String encode(final int depth) {
      return null;
    }

    protected Member lookupBinding(final String name) {
      return new Member(itemField, bindings.get(name), false);
    }
  }

  public static JSO parse(final InputStream in) throws IOException {
    final Envelope envelope = new Envelope();
    envelope.decode(in, JSOUtil.next(in));
    return envelope.item;
  }

  protected void decode(final InputStream in, char ch) throws IOException {
    boolean hasOpenBrace = false;
    final StringBuilder out = new StringBuilder();
    while (true) {
      if (ch == '{') {
        hasOpenBrace = true;
      }
      else if (ch == '}') {
        if (!hasOpenBrace)
          throw new IllegalArgumentException("Malformed JSON");

        return;
      }
      else if (ch == '"') {
        if (!hasOpenBrace)
          throw new IllegalArgumentException("Malformed JSON");
      }
      else if (ch == ':') {
        if (!hasOpenBrace)
          throw new IllegalArgumentException("Malformed JSON");

        final Member member = lookupBinding(out.toString());
        if (member == null)
          throw new IllegalArgumentException("Unexpected object: " + out);

        out.setLength(0);
        ch = JSOUtil.next(in);
        final boolean isArray = ch == '[';
        try {
          if (JSO.class.isAssignableFrom(member.type)) {
            if (isArray) {
              @SuppressWarnings("unchecked")
              final JSO[] value = objectFactory.recurse(in, (Class<? extends JSO>)member.type, 0);
              member.field.set(this, value);
            }
            else {
              final JSO value = (JSO)member.type.newInstance();
              value.decode(in, ch);
              member.field.set(this, value);
            }
          }
          else if (member.type == String.class) {
            if (isArray) {
              final String[] value = stringFactory.recurse(in, 0);
              member.field.set(this, value);
            }
            else {
              final String value = stringFactory.decode(in, ch);
              member.field.set(this, value);
            }
          }
          else if (member.type == Boolean.class) {
            if (isArray) {
              final Boolean[] value = booleanFactory.recurse(in, 0);
              member.field.set(this, value);
            }
            else {
              final Boolean value = booleanFactory.decode(in, ch);
              member.field.set(this, value);
            }
          }
          else if (member.type == Number.class) {
            if (isArray) {
              final Number[] value = numberFactory.recurse(in, 0);
              member.field.set(this, value);
            }
            else {
              final Number value = numberFactory.decode(in, ch);
              member.field.set(this, value);
            }
          }
        }
        catch (final Exception e) {
          throw new RuntimeException(e);
        }
      }
      else if (ch != ',') {
        out.append((char)ch);
      }

      ch = JSOUtil.next(in);
    }
  }
}