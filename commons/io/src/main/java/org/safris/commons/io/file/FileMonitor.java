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
import java.util.HashSet;
import java.util.Set;

public class FileMonitor {
  private final File file;
  private final int interval;
  private volatile long lastModifiedTime = 0;
  private volatile boolean deleted = false;
  private volatile boolean kill = false;
  private final Set<FileEventListener> listeners = new HashSet<FileEventListener>();

  public FileMonitor(File file, int interval) {
    if (file == null)
      throw new NullPointerException("file == null");

    this.file = file;
    this.interval = interval;
  }

  public void addListener(FileEventListener listener) {
    listeners.add(listener);
  }

  public void start() {
    if (!(deleted = !file.exists()))
      lastModifiedTime = file.lastModified();

    final FileMonitorRunner monitorThread = new FileMonitorRunner();
    Thread mainThread = null;
    final Set<Thread> threads = Thread.getAllStackTraces().keySet();
    for (final Thread thread : threads) {
      if ("main".equals(thread.getName())) {
        mainThread = thread;
        break;
      }
    }

    new FileMonitorKiller(mainThread, monitorThread).start();
    monitorThread.start();
  }

  private class FileMonitorRunner extends Thread {
    public void run() {
      try {
        while (true) {
          if (kill)
            break;

          long modifiedTime;
          if (!deleted && (deleted = !file.exists())) {
            new FileDeletedNotifier().start();
          }
          else if (lastModifiedTime < (modifiedTime = file.lastModified())) {
            lastModifiedTime = modifiedTime;
            deleted = false;
            new FileModifiedNotifier().start();
          }

          synchronized (this) {
            wait(interval);
          }

          if (kill)
            break;
        }
      }
      catch (InterruptedException e) {
      }
    }
  }

  private class FileMonitorKiller extends Thread {
    private final Thread criticalThread;
    private final Thread dependentThread;

    public FileMonitorKiller(Thread criticalThread, Thread dependentThread) {
      if (criticalThread == null)
        throw new NullPointerException("criticalThread == null");

      if (dependentThread == null)
        throw new NullPointerException("dependentThread == null");

      this.criticalThread = criticalThread;
      this.dependentThread = dependentThread;
    }

    public void run() {
      try {
        criticalThread.join();
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      kill = true;
      synchronized (dependentThread) {
        dependentThread.notify();
      }
    }
  }

  private class FileModifiedNotifier extends Thread {
    public void run() {
      for (FileEventListener listener : listeners)
        listener.onModify(file);
    }
  }

  private class FileDeletedNotifier extends Thread {
    public void run() {
      for (FileEventListener listener : listeners)
        listener.onDelete(file);
    }
  }
}
