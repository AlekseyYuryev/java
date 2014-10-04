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

package org.safris.xml.generator.lexer.schema.attribute;

import java.util.HashMap;
import java.util.Map;

public final class Final {
  private static final Map<String,Final> enums = new HashMap<String,Final>();

  public static final Final ALL = new Final("#all");
  public static final Final EXTENSION = new Final("extension");
  public static final Final RESTRICTION = new Final("restriction");
  public static final Final SUBSTITUTION = new Final("substitution");

  public static Final parseFinal(final String value) {
    return enums.get(value);
  }

  private final String value;

  private Final(final String value) {
    this.value = value;
    enums.put(value, this);
  }

  public String getValue() {
    return value;
  }

  public boolean equals(final Object obj) {
    if (!(obj instanceof Final))
      return false;

    return getValue().equals(((Final)obj).getValue());
  }

  public int hashCode() {
    return (getClass().getName() + toString()).hashCode();
  }

  public String toString() {
    return getValue();
  }
}