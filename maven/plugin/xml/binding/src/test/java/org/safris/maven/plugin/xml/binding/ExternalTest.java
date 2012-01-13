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

package org.safris.maven.plugin.xml.binding;

import java.io.File;
import org.junit.Ignore;
import org.junit.Test;
import org.safris.commons.exec.Processes;
import org.safris.commons.io.Files;

import static org.junit.Assert.*;

public class ExternalTest {
    private static final String[] DEBUG_VM_ARGS = null;
//  private static final String[] DEBUG_VM_ARGS = new String[]{"-Xdebug", "-Xnoagent", "-Djava.compiler=NONE", "-Xrunjdwp:transport=dt_socket,address=8000,server=y"};
    private static final String POM_PATH = "src/test/resources/xml/";

    public static void main(String[] args) throws Exception {
        new ExternalTest().testExternal();
    }

    @Test
  @Ignore
    public void testExternal() throws Exception {
        final Process process = Processes.forkSync(System.in, System.out, System.err, DEBUG_VM_ARGS, GeneratorMojo.class, POM_PATH + "pom-external.xml");
        if (process.exitValue() != 0)
            fail();

        Files.deleteAllOnExit(new File(POM_PATH + "target"));
    }
}
