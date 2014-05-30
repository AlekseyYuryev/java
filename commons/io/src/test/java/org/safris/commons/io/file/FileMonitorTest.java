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

package org.safris.commons.io.file;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public final class FileMonitorTest implements FileEventListener {
  public void onModify(final File file) {
    System.out.println(file.getName() + " modified.");
  }

  public void onDelete(final File file) {
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