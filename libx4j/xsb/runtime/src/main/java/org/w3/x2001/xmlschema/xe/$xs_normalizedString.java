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

package org.w3.x2001.xmlschema.xe;

import org.libx4j.xsb.runtime.MarshalException;
import org.w3c.dom.Element;

public abstract class $xs_normalizedString extends $xs_string {
  private static final long serialVersionUID = 3288734660660079355L;

  public $xs_normalizedString(final $xs_normalizedString binding) {
    super(binding);
  }

  public $xs_normalizedString(final String value) {
    super(value);
  }

  protected $xs_normalizedString() {
    super();
  }

  @Override
  public String text() {
    return super.text();
  }

  @Override
  public void text(final String text) {
    super.text(text);
  }

  protected void _$$decode(final String element, final String value) {
    super.text(value);
  }

  @Override
  protected String _$$encode(final Element parent) throws MarshalException {
    return super.text() != null ? super.text().toString() : "";
  }

  @Override
  public $xs_normalizedString clone() {
    return new $xs_normalizedString(this) {
      private static final long serialVersionUID = -2005930855161284183L;

      @Override
      protected $xs_normalizedString inherits() {
        return this;
      }
    };
  }
}