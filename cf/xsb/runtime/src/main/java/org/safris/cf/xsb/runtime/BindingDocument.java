/* Copyright (c) 2006 Seva Safris
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

package org.safris.cf.xsb.runtime;

import java.io.IOException;
import java.net.URL;

import org.safris.commons.xml.validator.ValidationException;
import org.xml.sax.InputSource;

public final class BindingDocument {
  private final URL url;
  private final Binding document;

  public BindingDocument(final URL url) throws IOException, ParseException, ValidationException {
    this.url = url;
    url.openConnection();
    document = Bindings.parse(new InputSource(url.openStream()));
  }

  public Binding getDocument() {
    return document;
  }

  public URL getURL() {
    return url;
  }
}