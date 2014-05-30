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

package org.safris.xml.generator.compiler.runtime;

public final class ParseException extends BindingException {
  private static final long serialVersionUID = -1115503587680711757L;

  public ParseException() {
    super();
  }

  public ParseException(final String message) {
    super(message);
  }

  public ParseException(final Throwable cause) {
    super(cause);
  }

  public ParseException(final String message, final Throwable cause) {
    super(message, cause);
  }
}