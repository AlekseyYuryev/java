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

package org.safris.commons.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class XMLError extends Error {
  private static final long serialVersionUID = 2679754040056450837L;

  private final Object lock = new Object();

  public XMLError() {
    super();
  }

  public XMLError(final String message) {
    super(message);
  }

  public XMLError(final Throwable cause) {
    if (cause instanceof InvocationTargetException)
      init(cause.getCause().getMessage(), cause.getCause());
    else
      init(cause.getMessage(), cause);
  }

  public XMLError(final String message, final Throwable cause) {
    if (cause instanceof InvocationTargetException)
      init(message != null ? message : cause.getCause().getMessage(), cause.getCause());
    else
      init(cause.getMessage(), cause);
  }

  protected final void init(final String message, final Throwable cause) {
    setMessage(message);
    overwriteCause(cause.getCause());
    setStackTrace(cause.getStackTrace());
  }

  protected final void overwriteCause(final Throwable cause) {
    if (cause == this)
      throw new IllegalArgumentException("Self-causation not permitted");

    try {
      synchronized (lock) {
        final Field detailMessageField = Throwable.class.getDeclaredField("cause");
        detailMessageField.setAccessible(true);
        detailMessageField.set(this, cause);
      }
    }
    catch (final SecurityException e) {
    }
    catch (final NoSuchFieldException e) {
    }
    catch (final IllegalAccessException e) {
    }
    catch (final IllegalArgumentException e) {
    }
  }

  protected final void setMessage(final String message) {
    try {
      synchronized (lock) {
        final Field detailMessageField = Throwable.class.getDeclaredField("detailMessage");
        detailMessageField.setAccessible(true);
        detailMessageField.set(this, message);
      }
    }
    catch (final SecurityException e) {
    }
    catch (final NoSuchFieldException e) {
    }
    catch (final IllegalAccessException e) {
    }
    catch (final IllegalArgumentException e) {
    }
  }
}