/* Copyright (c) 2015 lib4j
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

package org.libx4j.maven.plugin.jjb;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;
import org.libx4j.jjb.runtime.DecodeException;
import org.libx4j.jjb.runtime.EncodeException;
import org.libx4j.jjb.runtime.JSObject;

import jjb.type;

public class TypeTest {
  private static void setProperty(final JSObject jsObject, final String propertyName, final Object value) {
    // Set the value
    Adapter.property(jsObject, propertyName).set(value);

    // Assert that the get method returns the value that's been set
    Assert.assertEquals(value, Adapter.property(jsObject, propertyName).get());

    // Clear the property
    Adapter.property(jsObject, propertyName).clear();

    // Assert that after property.clear(), the get method returns null
    Assert.assertEquals(null, Adapter.property(jsObject, propertyName).get());

    // Set the value again
    Adapter.property(jsObject, propertyName).set(value);
  }

  @SafeVarargs
  @SuppressWarnings({"unchecked", "rawtypes"})
  private static void testPredicates(final JSObject jsObject, final String propertyName, final Unit ... predicates) {
    if (predicates.length == 0)
      throw new IllegalArgumentException("Must provide at least one predicate");

    for (final Unit predicate : predicates) {
      final Object validValue = Adapter.property(jsObject, propertyName).get();
      final Object condition = predicate.instigate(jsObject, propertyName);
      if (!predicate.validate(jsObject, propertyName, condition))
        Assert.fail("Failed " + predicate.getName() + " predicate:\n" + condition);

      Adapter.property(jsObject, propertyName).set(validValue);
    }
  }

  private static abstract class Unit<T> {
    private final String name;

    public Unit(final String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public abstract T instigate(final JSObject jsObject, final String propertyName);
    public abstract boolean validate(final JSObject jsObject, final String propertyName, final T result);
  }

  private static abstract class EncodeUnit extends Unit<Exception> {
    public EncodeUnit(final String name) {
      super(name);
    }

    public abstract void condition(final JSObject jsObject, final String propertyName);

    @Override
    public final Exception instigate(final JSObject jsObject, final String propertyName) {
      try {
        condition(jsObject, propertyName);
        jsObject.toString();
        throw new AssertionError();
      }
      catch (final EncodeException e) {
        return e;
      }
    }
  }

  private static abstract class DecodeUnit extends Unit<Exception> {
    public DecodeUnit(final String name) {
      super(name);
    }

    public abstract String condition(final JSObject jsObject, final String propertyName);

    @Override
    public final Exception instigate(final JSObject jsObject, final String propertyName) {
      try {
        JSObject.parse(jsObject.getClass(), new StringReader(condition(jsObject, propertyName)));
        throw new AssertionError();
      }
      catch (final DecodeException e) {
        return e;
      }
      catch (final IOException e) {
        throw new AssertionError();
      }
    }
  }

  private static final EncodeUnit nullEncode = new EncodeUnit("null encode") {
    @Override
    public void condition(final JSObject jsObject, final String propertyName) {
      Adapter.property(jsObject, propertyName).set(null);
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("cannot be null");
    }
  };

  private static final DecodeUnit nullDecode = new DecodeUnit("null decode") {
    @Override
    public String condition(final JSObject jsObject, final String propertyName) {
      return jsObject.toString().replaceAll("\"" + propertyName + "\":[^,]+", "\"" + propertyName + "\": null");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("cannot be null");
    }
  };

  private static final DecodeUnit requiredDecode = new DecodeUnit("required deode") {
    @Override
    public String condition(final JSObject jsObject, final String propertyName) {
      final String json = jsObject.toString().replaceAll("\"" + propertyName + "\":.*", "");
      return json;
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is required");
    }
  };

  private static final DecodeUnit lengthEncode = new DecodeUnit("length encode") {
    @Override
    public String condition(final JSObject jsObject, final String propertyName) {
      Adapter.property(jsObject, propertyName).set("dilav");
      return jsObject.toString().replaceAll("dilav", "invalid");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is longer than");
    }
  };

  private static final DecodeUnit lengthDecode = new DecodeUnit("length decode") {
    @Override
    public String condition(final JSObject jsObject, final String propertyName) {
      Adapter.property(jsObject, propertyName).set("xxxxx");
      return jsObject.toString().replaceAll("xxxxx", "this is too long");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is longer than");
    }
  };

  private static final EncodeUnit patternEncode = new EncodeUnit("pattern encode") {
    @Override
    public void condition(final JSObject jsObject, final String propertyName) {
      Adapter.property(jsObject, propertyName).set("invalid");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("does not match pattern");
    }
  };

  private static final DecodeUnit patternDecode = new DecodeUnit("pattern decode") {
    @Override
    public String condition(final JSObject jsObject, final String propertyName) {
      Adapter.property(jsObject, propertyName).set("dilav");
      return jsObject.toString().replaceAll("dilav", "invalid");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("does not match pattern");
    }
  };

  private static final Unit<JSObject> urlDecode = new Unit<JSObject>("urlDecode") {
    @Override
    public JSObject instigate(final JSObject jsObject, final String propertyName) {
      try {
        final String string = jsObject.toString();
        return JSObject.parse(jsObject.getClass(), new StringReader(string));
      }
      catch (final DecodeException | IOException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final JSObject result) {
      return "url encoded".equals(Adapter.property(result, propertyName).get());
    }
  };

  private static final Unit<String> urlEncode = new Unit<String>("urlEncode") {
    @Override
    public String instigate(final JSObject jsObject, final String propertyName) {
      return jsObject.toString();
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final String result) {
      return result.contains("url%20decoded");
    }
  };

  private static final Unit<Exception> encodeDecodeSuccess = new Unit<Exception>("success") {
    @Override
    public Exception instigate(final JSObject jsObject, final String propertyName) {
      try {
        JSObject.parse(jsObject.getClass(), new StringReader(jsObject.toString()));
        return null;
      }
      catch (final DecodeException | IOException e) {
        return e;
      }
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result == null;
    }
  };

  @Test
  public void testBooleans() {
    final type.Booleans booleans = new type.Booleans();
    setProperty(booleans, "booleanDefault", Boolean.TRUE);
    setProperty(booleans, "booleanNotRequired", Boolean.TRUE);
    setProperty(booleans, "booleanNotNull", Boolean.TRUE);

    testPredicates(booleans, "booleanDefault", requiredDecode);
    testPredicates(booleans, "booleanNotRequired", encodeDecodeSuccess);
    testPredicates(booleans, "booleanNotNull", nullEncode, nullDecode);
  }

  @Test
  public void testStrings() {
    final type.Strings strings = new type.Strings();
    setProperty(strings, "stringDefault", "valid");
    setProperty(strings, "stringNotRequired", "valid");
    setProperty(strings, "stringNotNull", "valid");
    setProperty(strings, "stringLength", "valid");
    setProperty(strings, "stringPattern", "valid");
    setProperty(strings, "stringUrlDecode", "url%20encoded");
    setProperty(strings, "stringUrlEncode", "url decoded");

    testPredicates(strings, "stringDefault", requiredDecode);
    testPredicates(strings, "stringNotRequired", encodeDecodeSuccess);
    testPredicates(strings, "stringNotNull", nullEncode, nullDecode);
    testPredicates(strings, "stringLength", lengthEncode, lengthDecode);
    testPredicates(strings, "stringPattern", patternEncode, patternDecode);
    testPredicates(strings, "stringUrlDecode", urlDecode);
    testPredicates(strings, "stringUrlEncode", urlEncode);
  }
}