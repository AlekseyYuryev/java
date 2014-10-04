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

package org.w3.x2001.xmlschema;

import org.safris.xml.generator.compiler.runtime.MarshalException;
import org.safris.xml.generator.compiler.runtime.ParseException;
import org.w3c.dom.Element;

public abstract class $xs_unsignedByte extends $xs_anySimpleType {
  public $xs_unsignedByte(final $xs_unsignedByte binding) {
    super(binding);
  }

  public $xs_unsignedByte(final Byte value) {
    super(value);
  }

  protected $xs_unsignedByte() {
    super();
  }

  public Byte text() {
    return (Byte)super.text();
  }

  public void text(final Byte text) {
    super.text(text);
  }

  protected void _$$decode(final Element parent, final String value) throws ParseException {
    super.text(Byte.parseByte(value));
  }

  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  public $xs_unsignedByte clone() {
    return new $xs_unsignedByte(this) {
      protected $xs_unsignedByte inherits() {
        return this;
      }
    };
  }
}