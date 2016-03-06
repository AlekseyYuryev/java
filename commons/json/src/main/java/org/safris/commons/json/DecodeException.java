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

package org.safris.commons.json;

public class DecodeException extends Exception {
  private static final long serialVersionUID = -1234230677110958751L;

  public DecodeException(final JSObject jsObject) {
    this(null, jsObject, null);
  }

  public DecodeException(final String message, final JSObject jsObject) {
    this(message, jsObject, null);
  }

  public DecodeException(final JSObject jsObject, final Throwable cause) {
    this(null, jsObject, cause);
  }

  public DecodeException(final String message, final JSObject jsObject, final Throwable cause) {
    super(message != null ? (jsObject != null ? message + "\n" + jsObject._bundle().getSpec() : message) : (jsObject != null ? jsObject._bundle().getSpec() : null), cause);
  }
}