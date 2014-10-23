/* Copyright (c) 2014 Seva Safris
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

package org.safris.maven.plugin.xml.validator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;

public class CachedResourceInput implements LSInput {
  private static final int BUFFER_SIZE = 256;

  private Reader reader;
  private InputStream inputStream;
  private boolean inputStreamConsumed;
  private String stringData;
  private String systemId;
  private String publicId;
  private String baseURI;
  private String encoding;
  private boolean certifiedText;

  public Reader getCharacterStream() {
    return this.reader;
  }

  public void setCharacterStream(final Reader characterStream) {
    this.reader = characterStream;
  }

  public InputStream getByteStream() {
    if (inputStream == null || inputStreamConsumed)
      return null;

    return new InputStream() {
      private final ByteArrayOutputStream buffer = new ByteArrayOutputStream(BUFFER_SIZE);

      public int read() throws IOException {
        final int in = inputStream.read();
        if (in == -1) {
          buffer.close();
          stringData = new String(buffer.toByteArray());
          inputStreamConsumed = true;
          return in;
        }

        buffer.write(in);
        return in;
      }
    };
  }

  public void setByteStream(final InputStream byteStream) {
    this.inputStream = byteStream;
  }

  public String getStringData() {
    return this.stringData;
  }

  public void setStringData(final String stringData) {
    this.stringData = stringData;
  }

  public String getSystemId() {
    return this.systemId;
  }

  public void setSystemId(final String systemId) {
    this.systemId = systemId;
  }

  public String getPublicId() {
    return this.publicId;
  }

  public void setPublicId(final String publicId) {
    this.publicId = publicId;
  }

  public String getBaseURI() {
    return this.baseURI;
  }

  public void setBaseURI(final String baseURI) {
    this.baseURI = baseURI;
  }

  public String getEncoding() {
    return this.encoding;
  }

  public void setEncoding(final String encoding) {
    this.encoding = encoding;
  }

  public boolean getCertifiedText() {
    return this.certifiedText;
  }

  public void setCertifiedText(final boolean certifiedText) {
    this.certifiedText = certifiedText;
  }
}