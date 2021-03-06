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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.libx4j.xsb.runtime.MarshalException;
import org.libx4j.xsb.runtime.ParseException;
import org.w3c.dom.Element;

@SuppressWarnings("unchecked")
public abstract class $xs_NMTOKENS extends $xs_anySimpleType {
  private static final long serialVersionUID = -5260386241351935007L;

  public $xs_NMTOKENS(final $xs_NMTOKENS binding) {
    super(binding);
  }

  public $xs_NMTOKENS(final List<String> value) {
    super(value);
  }

  protected $xs_NMTOKENS() {
    super();
  }

  @Override
  public List<String> text() {
    return (List<String>)super.text();
  }

  public <T extends List<String> & Serializable>void text(final T text) {
    super.text(text);
  }

  @Override
  protected void _$$decode(final Element parent, final String value) throws ParseException {
    if (value == null || value.length() == 0)
      return;

    super.text(new ArrayList<String>());
    final StringTokenizer tokenizer = new StringTokenizer(value);
    while (tokenizer.hasMoreTokens())
      ((List<String>)super.text()).add(tokenizer.nextToken());
  }

  @Override
  protected String _$$encode(final Element parent) throws MarshalException {
    if (super.text() == null || ((List<String>)super.text()).size() == 0)
      return null;

    String value = "";
    for (final String temp : (List<String>)super.text())
      value += " " + temp;

    return value.substring(1);
  }

  @Override
  public $xs_NMTOKENS clone() {
    return new $xs_NMTOKENS(this) {
      private static final long serialVersionUID = 4984687020734176771L;

      @Override
      protected $xs_NMTOKENS inherits() {
        return this;
      }
    };
  }
}