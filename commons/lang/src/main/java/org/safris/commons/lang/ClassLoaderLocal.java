/* Copyright (c) 2008 Seva Safris
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

package org.safris.commons.lang;

import com.sun.jmx.snmp.ThreadContext;

// FIXME: This is really a ClassLoaderThreadLocal because it uses the
// FIXME: ThreadContext class. Is there an alternative?
public final class ClassLoaderLocal<T> {
  private final ThreadContext threadContext;

  public ClassLoaderLocal(final ClassLoader classLoader) {
    try {
      final Class<ThreadContext> threadContextClass = (Class<ThreadContext>)classLoader.loadClass(ThreadContext.class.getName());
      threadContext = (ThreadContext)threadContextClass.getDeclaredMethod("getThreadContext").invoke(null);
    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void set(final T value) {
    threadContext.push("KEY", value);
  }

  public T get() {
    return (T)threadContext.get("KEY");
  }
}