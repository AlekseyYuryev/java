/*  Copyright 2008 Safris Technologies Inc.
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

package org.safris.commons.io.file;

import java.io.File;
import org.junit.Ignore;
import org.junit.Test;

public class FileMonitorTest implements FileEventListener {
    public void onModify(File file) {
        System.out.println(file.getName() + " modified.");
    }

    public void onDelete(File file) {
        System.out.println(file.getName() + " deleted.");
    }

    @Test
    @Ignore
    public void testFileMonitor() throws InterruptedException {
		final File testFile = new File("/tmp/file-monitor-test");
		testFile.deleteOnExit();

        final FileMonitor monitor = new FileMonitor(testFile, 5000);
        monitor.addListener(this);
        monitor.start();
        Thread.sleep(1000);
        System.out.println("main done!");
    }
}
