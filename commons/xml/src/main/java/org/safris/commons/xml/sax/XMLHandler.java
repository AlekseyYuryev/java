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

package org.safris.commons.xml.sax;

import java.util.LinkedHashMap;
import java.util.Map;

import org.safris.commons.lang.Paths;
import org.safris.commons.net.CachedURL;
import org.safris.commons.net.URLs;
import org.xml.sax.helpers.DefaultHandler;

public abstract class XMLHandler extends DefaultHandler {
  protected static String getPath(final String referrer, final String location) {
    return URLs.isAbsolute(location) ? location : Paths.newPath(Paths.getParent(referrer), location);
  }

  protected final CachedURL url;
  protected final Map<String,CachedURL> schemaLocations = new LinkedHashMap<String,CachedURL>();

  protected XMLHandler(final CachedURL url) {
    this.url = url;
  }
}
