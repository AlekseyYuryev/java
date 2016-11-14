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

package org.safris.cf.xde;

final class LogicalCondition<T> extends Condition<T> {
  private final Operator<LogicalCondition<?>> operator;
  private final Object a;
  private final Object b;

  protected LogicalCondition(final Operator<LogicalCondition<?>> operator, final Object a, final Object b) {
    this.operator = operator;
    this.a = a;
    this.b = b;
  }

  @Override
  protected Keyword<Subject<T>> parent() {
    return null;
  }

  @Override
  protected void serialize(final Serializable caller, final Serialization serialization) {
    if (a == null)
      throw new IllegalArgumentException("Left hand side of condition cannot be null");

    format(this, a, serialization);
    serialization.sql.append(" ").append(operator).append(" ");
    format(this, b, serialization);
  }
}