/* Copyright (c) 2006 Seva Safris
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

package org.safris.xsb.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;

public final class FinalDefault {
  private static final Map<String,FinalDefault> enums = new HashMap<String,FinalDefault>();

  public static final FinalDefault ALL = new FinalDefault("#all");
  public static final FinalDefault EXTENSION = new FinalDefault("extension");
  public static final FinalDefault RESTRICTION = new FinalDefault("restriction");
  public static final FinalDefault SUBSTITUTION = new FinalDefault("substitution");

  public static FinalDefault parseFinalDefault(final String value) {
    return enums.get(value);
  }

  private String value;

  private FinalDefault(final String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof FinalDefault))
      return false;

    return getValue().equals(((FinalDefault)obj).getValue());
  }

  @Override
  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  @Override
  public String toString() {
    return getValue();
  }
}