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

package org.safris.xml.generator.compiler.lang;

import org.safris.commons.xml.XMLError;

public final class CompilerError extends XMLError {
  private static final long serialVersionUID = -4619096950067417903L;

  public CompilerError() {
    super();
  }

  public CompilerError(final String message) {
    super(message);
  }

  public CompilerError(final Throwable cause) {
    super(cause);
  }

  public CompilerError(final String message, final Throwable cause) {
    super(message, cause);
  }
}