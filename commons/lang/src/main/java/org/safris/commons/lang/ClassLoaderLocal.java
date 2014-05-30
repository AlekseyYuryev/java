/*  Copyright Safris Software 2008
 *  
 *  This code is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.safris.commons.lang;

import java.lang.reflect.InvocationTargetException;

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
    catch (final InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    catch (final NoSuchMethodException e) {
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