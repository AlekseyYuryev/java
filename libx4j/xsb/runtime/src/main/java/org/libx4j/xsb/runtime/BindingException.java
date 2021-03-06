/* Copyright (c) 2006 lib4j
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

package org.libx4j.xsb.runtime;

import org.lib4j.xml.XMLException;

public class BindingException extends XMLException {
  private static final long serialVersionUID = 1328917257490102694L;

  public static final String ILLEGAL_NULL_ARGUMENT = "value cannot be null";

  public BindingException() {
    super();
  }

  public BindingException(final String message) {
    super(message);
  }

  public BindingException(final Throwable cause) {
    super(cause);
  }

  public BindingException(final String message, final Throwable cause) {
    super(message, cause);
  }
}