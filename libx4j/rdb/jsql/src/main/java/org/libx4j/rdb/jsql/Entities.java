/* Copyright (c) 2017 lib4j
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

package org.libx4j.rdb.jsql;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lib4j.lang.Strings;
import org.lib4j.xml.binding.Base64Binary;
import org.lib4j.xml.binding.DateTime;
import org.libx4j.rdb.dmlx.xe.$dmlx_binary;
import org.libx4j.rdb.dmlx.xe.$dmlx_blob;
import org.libx4j.rdb.dmlx.xe.$dmlx_clob;
import org.libx4j.rdb.dmlx.xe.$dmlx_data;
import org.libx4j.rdb.dmlx.xe.$dmlx_date;
import org.libx4j.rdb.dmlx.xe.$dmlx_dateTime;
import org.libx4j.rdb.dmlx.xe.$dmlx_decimal;
import org.libx4j.rdb.dmlx.xe.$dmlx_enum;
import org.libx4j.rdb.dmlx.xe.$dmlx_integer;
import org.libx4j.rdb.dmlx.xe.$dmlx_row;
import org.libx4j.rdb.dmlx.xe.$dmlx_time;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Element;
import org.libx4j.xsb.runtime.QName;
import org.w3.x2001.xmlschema.xe.$xs_anySimpleType;

public final class Entities {
  private static QName getName(final Class<?> cls) {
    return cls.getAnnotation(QName.class);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static type.Entity toEntity(final $dmlx_row row) {
    final Element element = (Element)row;
    final QName schemaName = getName(element.owner().getClass().getSuperclass());
    final QName entityName = getName(row.getClass().getSuperclass());

    try {
      final Class<?> binding = Class.forName(Entities.class.getPackage().getName() + "." + Strings.toInstanceCase(schemaName.localPart()) + "$" + Strings.toTitleCase(entityName.localPart()));
      final type.Entity entity = (type.Entity)binding.newInstance();
      final Iterator<? extends $xs_anySimpleType> attributeIterator = row.attributeIterator();
      while (attributeIterator.hasNext()) {
        final $xs_anySimpleType attribute = attributeIterator.next();
        if (attribute == null)
          continue;

        final Field field = binding.getField(Strings.toCamelCase(attribute.name().getLocalPart()));
        final type.DataType dataType = (type.DataType<?>)field.get(entity);
        final Object value = attribute.text();
        if (value == null)
          dataType.set(null);
        else if (attribute instanceof $dmlx_integer) {
          if (dataType.type() == BigInteger.class)
            dataType.set(value);
          else if (dataType.type() == Long.class)
            dataType.set(((BigInteger)value).longValue());
          else if (dataType.type() == Integer.class)
            dataType.set(((BigInteger)value).intValue());
          else if (dataType.type() == Short.class)
            dataType.set(((BigInteger)value).shortValue());
          else if (dataType.type() == Byte.class)
            dataType.set(((BigInteger)value).byteValue());
          else
            throw new UnsupportedOperationException("Unsupported Numeric type: " + dataType.type().getName());
        }
        else if (attribute instanceof $dmlx_decimal)
          dataType.set(value);
        else if (attribute instanceof $dmlx_date)
          dataType.set(LocalDate.parse((String)value));
        else if (attribute instanceof $dmlx_time)
          dataType.set(LocalTime.parse((String)value));
        else if (attribute instanceof $dmlx_dateTime)
          dataType.set(LocalDateTime.parse(((DateTime)value).toString()));
        else if (attribute instanceof $dmlx_enum) {
          for (final Object constant : ((type.ENUM)dataType).type().getEnumConstants()) {
            if (constant.toString().equals(value)) {
              dataType.set(constant);
              break;
            }
          }

          if (!dataType.wasSet())
            throw new IllegalArgumentException("'" + value + "' is not a valid value for " + dataType.name);
        }
        else if (attribute instanceof $dmlx_binary)
          dataType.set(((Base64Binary)value).getBytes());
        else if (attribute instanceof $dmlx_blob)
          dataType.set(new ByteArrayInputStream(((Base64Binary)value).getBytes()));
        else if (attribute instanceof $dmlx_clob)
          dataType.set(new StringReader((String)value));
        else
          dataType.set(value);
      }

      return entity;
    }
    catch (final ReflectiveOperationException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T extends type.Entity>T[] toEntities(final $dmlx_data data) {
    final Iterator<Binding> iterator = data.elementIterator();
    final List<type.Entity> entities = new ArrayList<type.Entity>();
    while (iterator.hasNext())
      entities.add(toEntity(($dmlx_row)iterator.next()));

    return (T[])entities.toArray(new type.Entity[entities.size()]);
  }

  private Entities() {
  }
}