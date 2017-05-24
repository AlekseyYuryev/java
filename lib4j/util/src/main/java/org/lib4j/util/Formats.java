/* Copyright (c) 2016 lib4j
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

package org.lib4j.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class Formats {
  public static ThreadLocal<SimpleDateFormat> createSimpleDateFormat(final String pattern, final Locale locale) {
    return new ThreadLocal<SimpleDateFormat>() {
      @Override
      protected SimpleDateFormat initialValue() {
        return new SimpleDateFormat(pattern, locale);
      }
    };
  }

  public static ThreadLocal<SimpleDateFormat> createSimpleDateFormat(final String pattern) {
    return createSimpleDateFormat(pattern, Locale.getDefault(Locale.Category.FORMAT));
  }

  public static ThreadLocal<DecimalFormat> createDecimalFormat(final String pattern) {
    return new ThreadLocal<DecimalFormat>() {
      @Override
      protected DecimalFormat initialValue() {
        return new DecimalFormat(pattern);
      }
    };
  }

  private Formats() {
  }
}