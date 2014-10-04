/* Copyright (c) 2009 Seva Safris
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

package org.safris.commons.net;

import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

public final class URLConnections {
  public static void setRequestProperties(final URLConnection urlConnection, final Properties properties) {
    if (urlConnection == null)
      throw new NullPointerException("urlConnection == null");

    if (properties == null)
      throw new NullPointerException("properties == null");

    for (final Map.Entry<Object,Object> entry : properties.entrySet())
      urlConnection.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
  }

  private URLConnections() {
  }
}