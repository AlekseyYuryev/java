/* Copyright (c) 2017 lib4j
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

package org.libx4j.xsb.runtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3.x2001.xmlschema.xe.$xs_anySimpleType;

public class CompositeAttributeStore implements Serializable {
  private static final long serialVersionUID = 5214136979828034837L;

  private final List<AttributeAudit<?>> audits = new ArrayList<AttributeAudit<?>>();

  public void add(final AttributeAudit<?> audit) {
    this.audits.add(audit);
  }

  public Iterator<? extends $xs_anySimpleType> iterator() {
    final Iterator<AttributeAudit<?>> iterator = audits.iterator();
    return new Iterator<$xs_anySimpleType>() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public $xs_anySimpleType next() {
        return iterator.next().getAttribute();
      }
    };
  }
}