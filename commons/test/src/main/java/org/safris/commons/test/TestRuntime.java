/*  Copyright Safris Software 2009
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

package org.safris.commons.test;

public final class TestRuntime {
  public static boolean isInIDE() {
    // See if the test is being invoked from IDEA
    if (System.getProperty("idea.launcher.port") != null)
      return true;

    // See if the test is being invoked from CodeGuide
    if (System.getProperty("java.compiler") != null || System.getProperty("java.library.path").contains("codeguide"))
      return true;

    // See if the test is being invoked from Eclipse
    if (System.getProperty("java.library.path").contains("eclipse"))
      return true;

    return false;
  }

  public static boolean isInTest() {
    if (isInTest != null)
      return isInTest;

    for (final StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace())
      if ("junit.framework.TestCase".equals(stackTraceElement.getClassName()) || "org.junit.internal.runners.MethodRoadie".equals(stackTraceElement.getClassName()))
        return isInTest = true;

    return isInTest = false;
  }

  private static Boolean isInTest = null;

  private TestRuntime() {
  }
}