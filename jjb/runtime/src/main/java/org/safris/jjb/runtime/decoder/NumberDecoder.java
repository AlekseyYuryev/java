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

package org.safris.jjb.runtime.decoder;

import java.io.IOException;

import org.safris.commons.util.RewindableReader;
import org.safris.jjb.runtime.Binding;
import org.safris.jjb.runtime.JSObjectBase;

public class NumberDecoder extends Decoder<Number> {
  @Override
  protected Number[] newInstance(final int depth) {
    return new Double[depth];
  }

  @Override
  public Number decode(final RewindableReader reader, char ch, final Binding<?> binding) throws IOException {
    if (('0' > ch || ch > '9') && ch != '-') {
      if (JSObjectBase.isNull(ch, reader))
        return null;

      throw new IllegalArgumentException("Malformed JSON");
    }

    final StringBuilder value = new StringBuilder();
    do {
      value.append(ch);
      reader.mark(1);
    }
    while ('0' <= (ch = JSObjectBase.nextAny(reader)) && ch <= '9' || ch == '.' || ch == 'e' || ch == 'E' || ch == '-');
    reader.reset();

    final String number = value.toString();
    if (number.contains(".") || number.contains("e") || number.contains("E"))
      return Double.parseDouble(number);

    return Long.parseLong(number);
  }
}