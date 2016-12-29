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

import java.util.Map;

public class XMLDocument {
  private final boolean isXSD;
  private final Map<String,SchemaLocation> schemaReferences;

  public XMLDocument(final Map<String,SchemaLocation> schemaReferences, final boolean isXSD) {
    this.schemaReferences = schemaReferences;
    this.isXSD = isXSD;
  }

  public Map<String,SchemaLocation> getSchemaReferences() {
    return schemaReferences;
  }

  public boolean isXSD() {
    return isXSD;
  }
}