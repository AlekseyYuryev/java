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
import java.util.Map;

public abstract class JSObject extends JSObjectUtil {
  public static JSObject parse(final InputStream in) throws DecodeException, IOException {
    return decode(in, next(in), null);
  }

  protected abstract String _name();
  protected abstract String _encode(final int depth);
  protected abstract Map<String,Binding<?>> _bindings();

  protected abstract JSBundle _bundle();
}