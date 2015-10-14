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

package org.safris.commons.json.decoder;

import java.io.IOException;
import java.io.InputStream;

import org.safris.commons.json.JSObjectBase;

public class StringDecoder extends Decoder<String> {
  protected String[] newInstance(final int depth) {
    return new String[depth];
  }

  public String decode(final InputStream in, char ch) throws IOException {
    if (ch != '"') {
      if (JSObjectBase.isNull(ch, in))
        return null;

      throw new IllegalArgumentException("Malformed JSON");
    }

    final StringBuilder value = new StringBuilder();
    while ((ch = JSObjectBase.nextAny(in)) != '"')
      value.append(ch);

    return value.toString();
  }
}