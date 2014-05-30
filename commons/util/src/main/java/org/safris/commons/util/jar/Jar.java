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

package org.safris.commons.util.jar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public final class Jar {
  private final File jarFile;
  private final FileOutputStream stream;
  private final JarOutputStream out;
  private final Collection<JarEntry> entries = new HashSet<JarEntry>();

  public Jar(final File file) throws IOException {
    this.jarFile = file;
    if (!file.getParentFile().exists())
      file.getParentFile().mkdirs();

    this.stream = new FileOutputStream(file);
    this.out = new JarOutputStream(stream, new Manifest());
  }

  public File getFile() {
    return jarFile;
  }

  public void addEntry(final String fileName, final byte[] bytes) throws IOException {
    synchronized (out) {
      // Add archive entry
      final JarEntry jarEntry = new JarEntry(fileName);
      entries.add(jarEntry);
      jarEntry.setTime(System.currentTimeMillis());

      // Write file to archive
      try {
        out.putNextEntry(jarEntry);
        out.write(bytes);
      }
      catch (final IOException e) {
        if ("no current ZIP entry".equals(e.getMessage()) || "Stream closed".equals(e.getMessage()))
          return;
      }
    }
  }

  public Collection<JarEntry> getEntries() {
    return entries;
  }

  public void close() throws IOException {
    synchronized (out) {
      out.close();
      stream.close();
    }
  }
}