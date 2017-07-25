package org.libx4j.jjb.runtime;

import java.lang.reflect.Field;

import org.libx4j.jjb.runtime.JSObject;
import org.libx4j.jjb.runtime.Property;

public final class Adapter {
  @SuppressWarnings("rawtypes")
  public static Property property(final JSObject jsObject, final String propertyName) {
    try {
      final Field field = jsObject.getClass().getField(propertyName);
      return (Property)field.get(jsObject);
    }
    catch (final IllegalAccessException | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
  }

  private Adapter() {
  }
}