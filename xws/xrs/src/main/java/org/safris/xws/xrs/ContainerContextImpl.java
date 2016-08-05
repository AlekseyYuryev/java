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

package org.safris.xws.xrs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public abstract class ContainerContextImpl {
  protected static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
    @Override
    protected SimpleDateFormat initialValue() {
      return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    }
  };

  private final Locale locale;

  protected ContainerContextImpl(final Locale locale) {
    this.locale = locale;
  }

  protected abstract MultivaluedMap<String,String> getStringHeaders();

  public final String getHeaderString(final String name) {
    return getStringHeaders().getFirst(name);
  }

  public final Date getDate() {
    final String date = getStringHeaders().getFirst(HttpHeaders.DATE);
    try {
      return date == null ? null : dateFormat.get().parse(date);
    }
    catch (final ParseException e) {
      getStringHeaders().remove(HttpHeaders.DATE);
      return null;
    }
  }

  public Locale getLanguage() {
    return locale;
  }
}