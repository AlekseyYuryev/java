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

package org.safris.commons.formatter;

public abstract class FormatModule {
  private static int depth = 0;
  private static FormatModule lastModule = null;

  public static void restetDepth() {
    depth = 0;
  }

  protected void increaseDepth() {
    depth++;
  }

  protected void decreaseDepth() {
    depth--;
  }

  protected int getDepth() {
    return depth;
  }

  protected static FormatModule getLastModule() {
    return lastModule;
  }

  protected static void setLastModule(FormatModule module) {
    FormatModule.lastModule = module;
  }

  abstract String format(String formated, String token);
}
