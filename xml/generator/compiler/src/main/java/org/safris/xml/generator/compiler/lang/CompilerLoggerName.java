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

package org.safris.xml.generator.compiler.lang;

import org.safris.commons.logging.LoggerName;

public final class CompilerLoggerName extends LoggerName {
  public static final CompilerLoggerName PLAN = new CompilerLoggerName("PLAN");
  public static final CompilerLoggerName WRITE = new CompilerLoggerName("WRITE");

  public CompilerLoggerName(final String name) {
    super(name);
  }
}