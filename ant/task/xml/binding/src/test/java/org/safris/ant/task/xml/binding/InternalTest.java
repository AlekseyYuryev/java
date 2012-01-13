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

package org.safris.ant.task.xml.binding;

import org.junit.Test;
import org.safris.commons.exec.Processes;

import static org.junit.Assert.*;

public class InternalTest {
  private static final String[] DEBUG_VM_ARGS = null;
//  private static final String[] DEBUG_VM_ARGS = new String[]{"-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,address=8000,server=y"};
  private static final String BUILD_PATH = "src/test/resources/xml/";

  public static void main(String[] args) throws Exception {
    new InternalTest().testInternal();
  }

  @Test
  public void testInternal() throws Exception {
    final Process process = Processes.forkSync(System.in, System.out, System.err, DEBUG_VM_ARGS, GeneratorTask.class, new String[]{BUILD_PATH + "build-internal.xml"});
    if (process.exitValue() != 0)
      fail();
  }
}
