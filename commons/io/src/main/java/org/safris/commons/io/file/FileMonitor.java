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
        for (Thread thread : threads) {
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
