/* Copyright (c) 2016 Seva Safris
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

package org.safris.dbx.jsql;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;

import org.safris.dbx.jsql.type.DATE;
import org.safris.dbx.jsql.type.DATETIME;
import org.safris.dbx.jsql.type.TIME;

public abstract class GenerateOn<T> {
  public static final GenerateOn<Number> INCREMENT = new GenerateOn<Number>() {
    private Number generate(final type.DataType<Number> dataType) {
      if (dataType.get() == null)
        throw new IllegalArgumentException("value is missing");

      if (dataType.get() instanceof Long)
        return (long)(dataType.get().longValue() + 1l);

      if (dataType.get() instanceof Integer)
        return (int)(dataType.get().intValue() + 1);

      if (dataType.get() instanceof Double)
        return (double)(dataType.get().doubleValue() + 1d);

      if (dataType.get() instanceof Float)
        return (float)(dataType.get().floatValue() + 1f);

      if (dataType.get() instanceof Short)
        return (short)(dataType.get().shortValue() + 1);

      return (byte)(dataType.get().byteValue() + 1);
    }

    @Override
    public Number generateStatic(final type.DataType<Number> dataType) {
      return generate(dataType);
    }

    @Override
    public String generateDynamic(final Serialization serialization, final type.DataType<Number> dataType) {
      return dataType.name + " + 1";
    }
  };

  public static final GenerateOn<Temporal> TIMESTAMP = new GenerateOn<Temporal>() {
    private Temporal generate(final type.DataType<? extends Temporal> dataType) {
      if (dataType instanceof DATE)
        return LocalDate.now();

      if (dataType instanceof TIME)
        return LocalTime.now();

      if (dataType instanceof DATETIME)
        return LocalDateTime.now();

      throw new UnsupportedOperationException("Unknown type: " + dataType.getClass().getName());
    }

    @Override
    public Temporal generateStatic(final type.DataType<Temporal> dataType) {
      return generate(dataType);
    }

    @Override
    public String generateDynamic(final Serialization serialization, final type.DataType<Temporal> dataType) throws IOException {
      dataType.set(generate(dataType));
      serialization.addParameter(dataType);
      return serialization.serializer.getPreparedStatementMark(dataType);
    }
  };

  public static final GenerateOn<String> UUID = new GenerateOn<String>() {
    private String generate(final type.DataType<String> dataType) {
      return java.util.UUID.randomUUID().toString().toUpperCase();
    }

    @Override
    public String generateStatic(final type.DataType<String> dataType) {
      return generate(dataType);
    }

    @Override
    public String generateDynamic(final Serialization serialization, final type.DataType<String> dataType) {
      return generate(dataType);
    }
  };

  public abstract T generateStatic(final type.DataType<T> dataType);
  public abstract String generateDynamic(final Serialization serialization, final type.DataType<T> dataType) throws IOException;
}