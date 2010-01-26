/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
