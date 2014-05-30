/*  Copyright Safris Software 2006
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

public final class PackageNotFoundException extends Exception {
  /**
   * use serialVersionUID from JDK 1.1.X for interoperability
   */
  private static final long serialVersionUID = 4963238462943629433L;

  /**
   * This field holds the exception cause if the
   * PackageNotFoundException(final String s, final Throwable cause) constructor was
   * used to instantiate the object
   * @serial
   */
  private Throwable cause;

  /**
   * Constructs a <code>PackageNotFoundException</code> with no detail message.
   */
  public PackageNotFoundException() {
    super((Throwable)null);  // Disallow initCause
  }

  /**
   * Constructs a <code>PackageNotFoundException</code> with the
   * specified detail message.
   *
   * @param   message   the detail message.
   */
  public PackageNotFoundException(final String message) {
    super(message, null);  //  Disallow initCause
  }

  /**
   * Constructs a <code>PackageNotFoundException</code> with the
   * specified detail message and optional exception that was
   * raised while loading the class.
   *
   * @param message the detail message
   * @param cause the exception that was raised while loading the class
   */
  public PackageNotFoundException(final String message, final Throwable cause) {
    super(message, null);  //  Disallow initCause
    this.cause = cause;
  }

  /**
   * Returns the exception that was raised if an error occurred while
   * attempting to load the class. Otherwise, returns <tt>null</tt>.
   *
   * <p>This method predates the general-purpose exception chaining facility.
   * The {@link Throwable#getCause()} method is now the preferred means of
   * obtaining this information.
   *
   * @return the <code>Exception</code> that was raised while loading a class
   */
  public Throwable getException() {
    return cause;
  }

  /**
   * Returns the cause of this exception (final the exception that was raised
   * if an error occurred while attempting to load the class; otherwise
   * <tt>null</tt>).
   *
   * @return  the cause of this exception.
   */
  public Throwable getCause() {
    return cause;
  }
}