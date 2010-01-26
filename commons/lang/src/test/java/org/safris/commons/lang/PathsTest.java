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

package org.safris.commons.lang;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class PathsTest {
    public static void main(String[] args) throws Exception {
        final PathsTest pathsTest = new PathsTest();
        pathsTest.testGetName();
        pathsTest.testGetParent();
    }

    @Test
    public void testGetName() throws Exception {
        final Map<String,String> paths = new HashMap<String,String>();
        paths.put("share", "file:///usr/share/../share");
        paths.put("lib", "file:///usr/share/../share/../lib");
        paths.put("var", "/usr/share/../share/../lib/../../var");
        paths.put("var", "/usr/share/../share/../lib/../../var/");
        paths.put("resolv.conf", "/etc/resolv.conf");
        paths.put("name", "name");

        for (Map.Entry<String,String> entry : paths.entrySet())
            assertEquals(entry.getKey(), Paths.getName(entry.getValue()));

        assertNull(Paths.getName(null));
    }

    @Test
    public void testGetParent() throws Exception {
        final Map<String,String> urls = new HashMap<String,String>();
        urls.put("file:///usr", "file:///usr/share/../share");
        urls.put("/usr", "/usr/share/../share/..");
        urls.put("/", "/usr/share/../share/../../");
        urls.put("file:///usr/local", "file:///usr/local/bin/../lib/../bin");

        for (Map.Entry<String,String> entry : urls.entrySet())
            assertEquals(entry.getKey(), Paths.getParent((entry.getValue())));

        assertNull(Paths.getParent(null));
    }
}
