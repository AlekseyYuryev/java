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

package org.safris.commons.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.safris.commons.io.input.ReviewableInputStream;

public class CachedURL {
  protected final URL url;
  private ReviewableInputStream in;

  public CachedURL(final URL url) {
    this.url = url;
  }

  public CachedURL(final String spec) throws MalformedURLException {
    this.url = new URL(spec);
  }

  public final InputStream openStream() throws IOException {
    return in == null ? in = new ReviewableInputStream(url.openStream()) : in;
  }

  public void destroy() throws IOException {
    in.destroy();
  }

  public void reset() throws IOException {
    in.close();
  }

  public boolean isLocal() {
    return URLs.isLocal(url);
  }

  @Override
  public String toString() {
    return url.toString();
  }
}