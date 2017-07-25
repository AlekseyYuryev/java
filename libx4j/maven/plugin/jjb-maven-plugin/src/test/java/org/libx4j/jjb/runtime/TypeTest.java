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

package org.libx4j.jjb.runtime;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import jjb.type;

public class TypeTest {
  private static void setProperty(final JSObject jsObject, final String propertyName, final Object ... value) {
    if (value.length == 0)
      throw new IllegalArgumentException("value.length == 0");

    final Object val = value.length == 1 ? value[0] : Arrays.asList(value);
    // Set the value
    Adapter.property(jsObject, propertyName).set(val);

    // Assert that the get method returns the value that's been set
    Assert.assertEquals(val, Adapter.property(jsObject, propertyName).get());

    // Clear the property
    Adapter.property(jsObject, propertyName).clear();

    // Assert that after property.clear(), the get method returns null
    Assert.assertEquals(null, Adapter.property(jsObject, propertyName).get());

    // Set the value again
    Adapter.property(jsObject, propertyName).set(val);
  }

  @SafeVarargs
  @SuppressWarnings({"unchecked", "rawtypes"})
  private static void testUnits(final JSObject jsObject, final String propertyName, final Unit ... units) {
    final JSObject clone = jsObject.clone();
    if (units.length == 0)
      throw new IllegalArgumentException("Must provide at least one unit");

    for (final Unit unit : units) {
      final Object validValue = Adapter.property(clone, propertyName).get();
      final Object condition = unit.instigate(clone, propertyName);
      if (!unit.validate(clone, propertyName, condition))
        Assert.fail("Failed \"" + unit.getName() + "\" unit:\n" + condition);

      Adapter.property(clone, propertyName).set(validValue);
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
        final String json = jsObject.toString();
        throw new AssertionError("Expected " + getClass().getSimpleName() + " to instigate condition \"" + getName() + "\"\n" + json);
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
        throw new AssertionError("Expected " + getClass().getSimpleName() + " to instigate condition");
      }
      catch (final DecodeException e) {
        return e;
      }
      catch (final IOException e) {
        throw new RuntimeException(e);
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

  private static final DecodeUnit requiredDecode = new DecodeUnit("required decode") {
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

  private static final EncodeUnit lengthEncode = new EncodeUnit("length encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList("invalid") : "invalid");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is longer than");
    }
  };

  private static final DecodeUnit lengthDecode = new DecodeUnit("length decode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList("xxxxx") : "xxxxx");
      return jsObject.toString().replaceAll("xxxxx", property.binding.array ? "[this is too long]" : "this is too long");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is longer than");
    }
  };

  private static final EncodeUnit patternEncode = new EncodeUnit("pattern encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList("invalid") : "invalid");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("does not match pattern");
    }
  };

  private static final DecodeUnit patternDecode = new DecodeUnit("pattern decode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList("dilav") : "dilav");
      return jsObject.toString().replaceAll("dilav", property.binding.array ? "[invalid]" : "invalid");
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
      return Adapter.property(result, propertyName).get().toString().contains("url encoded");
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

  private static final EncodeUnit minEncode = new EncodeUnit("min encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigDecimal("-1.1")) : new BigDecimal("-1.1"));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is less than");
    }
  };

  private static final DecodeUnit minDecode = new DecodeUnit("min decode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigDecimal("0.0")) : new BigDecimal("0.0"));
      return jsObject.toString().replaceAll("0.0", "-1");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is less than");
    }
  };

  private static final EncodeUnit maxEncode = new EncodeUnit("max encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigDecimal("1.1")) : new BigDecimal("1.1"));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is greater than");
    }
  };

  private static final DecodeUnit maxDecode = new DecodeUnit("max decode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigDecimal("0.0")) : new BigDecimal("0.0"));
      return jsObject.toString().replaceAll("0.0", "1.1");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is greater than");
    }
  };

  private static final EncodeUnit integerEncode = new EncodeUnit("integer encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigDecimal("3.1415")) : new BigDecimal("3.1415"));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is not an \"integer\"");
    }
  };

  private static final DecodeUnit integerDecode = new DecodeUnit("integer decode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      property.set(property.binding.array ? Collections.singletonList(new BigInteger("989898")) : new BigInteger("989898"));
      return jsObject.toString().replaceAll("989898", "3.1415");
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("is not an \"integer\"");
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

  private static final EncodeUnit incorrectTypeEncode = new EncodeUnit("incorrect type encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      final Object value = Number.class.isAssignableFrom(property.binding.type) ? "oops" : property.binding.type == Boolean.class ? BigInteger.ZERO : property.binding.type == String.class ? Boolean.FALSE : "{}";
      property.set(property.binding.array ? Collections.singletonList(value) : value);
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("cannot be encoded");
    }
  };

  private static final DecodeUnit incorrectTypeDecode = new DecodeUnit("incorrect type decode") {
    @Override
    @SuppressWarnings("rawtypes")
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      final Object value = Number.class.isAssignableFrom(property.binding.type) ? "\"oops\"" : property.binding.type == Boolean.class ? BigInteger.ZERO : property.binding.type == String.class ? "false" : "{}";
      return jsObject.toString().replaceAll("\"" + property.binding.name + "\":[^,]+", "\"" + property.binding.name + "\":" + (property.binding.array ? "[" + value + "]" : value));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("Illegal char");
    }
  };

  private static final EncodeUnit incorrectArrayEncode = new EncodeUnit("incorrect array encode") {
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      final Object value = Number.class.isAssignableFrom(property.binding.type) ? BigInteger.ZERO : property.binding.type == String.class ? "valid" : property.binding.type == Boolean.class ? "true" : "{}";
      property.set(property.binding.array ? value : Collections.singletonList(value));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("cannot be encoded");
    }
  };

  private static final DecodeUnit incorrectArrayDecode = new DecodeUnit("incorrect array decode") {
    @Override
    @SuppressWarnings("rawtypes")
    public String condition(final JSObject jsObject, final String propertyName) {
      final Property property = Adapter.property(jsObject, propertyName);
      final Object value = Number.class.isAssignableFrom(property.binding.type) ? BigInteger.ZERO : property.binding.type == String.class ? "\"valid\"" : property.binding.type == Boolean.class ? "true" : "{}";
      return jsObject.toString().replaceAll("\"" + property.binding.name + "\":[^,]+", "\"" + property.binding.name + "\":" + (property.binding.array ? value : "[" + value + "]"));
    }

    @Override
    public boolean validate(final JSObject jsObject, final String propertyName, final Exception result) {
      return result.getMessage().contains("incompatible with");
    }
  };

  @Test
  public void testBooleans() {
    final type.Booleans jsObject = new type.Booleans();
    setProperty(jsObject, "booleanDefault", Boolean.TRUE);
    setProperty(jsObject, "booleanNotRequired", Boolean.TRUE);
    setProperty(jsObject, "booleanNotNull", Boolean.TRUE);

    testUnits(jsObject, "booleanDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "booleanNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "booleanNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }

  @Test
  public void testBooleanArrays() {
    final type.BooleanArrays jsObject = new type.BooleanArrays();
    setProperty(jsObject, "booleanArrayDefault", Boolean.TRUE, null, Boolean.FALSE);
    setProperty(jsObject, "booleanArrayNotRequired", Boolean.TRUE, null, Boolean.FALSE);
    setProperty(jsObject, "booleanArrayNotNull", Boolean.TRUE, null, Boolean.FALSE);

    testUnits(jsObject, "booleanArrayDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "booleanArrayNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "booleanArrayNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }

  @Test
  public void testStrings() {
    final type.Strings jsObject = new type.Strings();
    setProperty(jsObject, "stringDefault", "valid");
    setProperty(jsObject, "stringNotRequired", "valid");
    setProperty(jsObject, "stringNotNull", "valid");
    setProperty(jsObject, "stringLength", "valid");
    setProperty(jsObject, "stringPattern", "valid");
    setProperty(jsObject, "stringUrlDecode", "url%20encoded");
    setProperty(jsObject, "stringUrlEncode", "url decoded");

    testUnits(jsObject, "stringDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringLength", lengthEncode, lengthDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringPattern", patternEncode, patternDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringUrlDecode", urlDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringUrlEncode", urlEncode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }

  @Test
  public void testStringArrays() {
    final type.StringArrays jsObject = new type.StringArrays();
    setProperty(jsObject, "stringArrayDefault", "valid", null, "valid");
    setProperty(jsObject, "stringArrayNotRequired", "valid", null, "valid");
    setProperty(jsObject, "stringArrayNotNull", "valid", null, "valid");
    setProperty(jsObject, "stringArrayLength", "valid", null, "valid");
    setProperty(jsObject, "stringArrayPattern", "valid", null, "valid");
    setProperty(jsObject, "stringArrayUrlDecode", "url%20encoded", "valid", null, "valid");
    setProperty(jsObject, "stringArrayUrlEncode", "url decoded", "valid", null, "valid");

    testUnits(jsObject, "stringArrayDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayLength", lengthEncode, lengthDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayPattern", patternEncode, patternDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayUrlDecode", urlDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "stringArrayUrlEncode", urlEncode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }

  @Test
  public void testNumbers() {
    final type.Numbers jsObject = new type.Numbers();
    setProperty(jsObject, "numberDefault", new BigDecimal("3.1415"));
    setProperty(jsObject, "numberNotRequired", new BigDecimal("2.7182"));
    setProperty(jsObject, "numberNotNull", new BigDecimal("9.8106"));
    setProperty(jsObject, "numberMin", new BigDecimal("3.1415"));
    setProperty(jsObject, "numberMax", new BigDecimal("-2.7182"));
    setProperty(jsObject, "numberInteger", BigInteger.TEN);

    testUnits(jsObject, "numberDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberMin", minEncode, minDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberMax", maxEncode, maxDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberInteger", integerEncode, integerDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }

  @Test
  public void testNumberArrays() {
    final type.NumberArrays jsObject = new type.NumberArrays();
    setProperty(jsObject, "numberArrayDefault", new BigDecimal("3.1415"), null, new BigDecimal("2.7182"));
    setProperty(jsObject, "numberArrayNotRequired", new BigDecimal("2.7182"), null, new BigDecimal("9.8106"));
    setProperty(jsObject, "numberArrayNotNull", new BigDecimal("9.8106"), null, new BigDecimal("3.1415"));
    setProperty(jsObject, "numberArrayMin", new BigDecimal("3.1415"), null, new BigDecimal("9.8106"));
    setProperty(jsObject, "numberArrayMax", new BigDecimal("-2.7182"), null, new BigDecimal("-3.1415"));
    setProperty(jsObject, "numberArrayInteger", BigInteger.TEN, null, BigInteger.ZERO);

    testUnits(jsObject, "numberArrayDefault", requiredDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberArrayNotRequired", encodeDecodeSuccess, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberArrayNotNull", nullEncode, nullDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberArrayMin", minEncode, minDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberArrayMax", maxEncode, maxDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
    testUnits(jsObject, "numberArrayInteger", integerEncode, integerDecode, incorrectTypeEncode, incorrectTypeDecode, incorrectArrayEncode, incorrectArrayDecode);
  }
}