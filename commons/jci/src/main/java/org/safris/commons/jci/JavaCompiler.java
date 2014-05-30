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

package org.safris.commons.jci;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.safris.commons.exec.Processes;
import org.safris.commons.io.Files;
import org.safris.commons.lang.Resources;
import org.safris.commons.util.jar.Jar;
import org.safris.commons.util.zip.CachedFile;
import org.safris.commons.util.zip.Zips;

public final class JavaCompiler {
  private final Collection<File> classpathFiles;
  private final File destDir;
  private final Jar destJar;

  public JavaCompiler(final File destDir, final Collection<File> classpath) {
    this.classpathFiles = classpath;
    if (destDir == null)
      throw new IllegalArgumentException("dir cannot be null");

    if (destDir.isFile())
      throw new IllegalArgumentException(destDir + " is a file!");

    this.destDir = destDir;
    this.destJar = null;
  }

  public JavaCompiler(final Jar destJar, final Collection<File> classpath) {
    this.classpathFiles = classpath;
    this.destDir = null;
    if (destJar == null)
      throw new IllegalArgumentException("jar cannot be null");

    if (destDir.isDirectory())
      throw new IllegalArgumentException(destDir + " is a directory!");

    this.destJar = destJar;
  }

  public void compile(final Collection<File> javaSources) throws Exception {
    if (destDir != null)
      toDir(destDir, javaSources);
    else
      toJar(destJar, javaSources);
  }

  private void toJar(final Jar destJar, final Collection<File> javaSources) throws Exception {
    if (javaSources == null || javaSources.size() == 0)
      return;

    final File tempDir = new File(UUID.randomUUID().toString());
    toDir(tempDir, javaSources);
    final FileFilter fileFilter = new FileFilter() {
      public boolean accept(final File pathname) {
        return true;
      }
    };

    Files.deleteAllOnExit(tempDir, fileFilter);
    try {
      toDir(tempDir, javaSources);
      final Collection<File> files = Files.listAll(tempDir);
      final Collection<CachedFile> selected = new ArrayList<CachedFile>();
      for (final File file : files) {
        if (!file.isFile() || !file.getName().endsWith(".class"))
          continue;

        selected.add(new CachedFile(file.getPath().substring(tempDir.getPath().length() + 1), Files.getBytes(file)));
      }

      Zips.add(destJar.getFile(), selected);
    }
    finally {
      Files.deleteAllOnExit(tempDir, fileFilter);
    }
  }

  private void toDir(final File destDir, final Collection<File> javaSources) throws Exception {
    if (javaSources == null || javaSources.size() == 0)
      return;

    if (!destDir.exists())
      if (!destDir.mkdirs())
        throw new IllegalArgumentException("Could not create directory " + destDir);

    String classpath = "";
    if (classpathFiles != null)
      for (final File classpathFile : classpathFiles)
        if (classpathFile != null)
          classpath += classpathFile + File.pathSeparator;

    classpath += System.getProperty("java.class.path");
    final File locationBase = Resources.getLocationBase(JavaCompiler.class);
    if (locationBase != null)
      classpath = locationBase.getAbsolutePath() + File.pathSeparator + classpath;

    final File tempFile = File.createTempFile("xml", ".tmp");
    final FileOutputStream out = new FileOutputStream(tempFile);
    for (final File javaSource : javaSources) {
      out.write(javaSource.getAbsolutePath().getBytes());
      out.write('\n');
    }

    out.close();

    int i = 0;
    final String[] args = new String[9];
    args[i++] = "javac";
    args[i++] = "-Xlint:none";
    args[i++] = "-J-Xmx256m";
    args[i++] = "-J-XX:MaxPermSize=128m";
    args[i++] = "-d";
    args[i++] = destDir.getAbsolutePath();
    args[i++] = "-cp";
    args[i++] = classpath;
    args[i++] = "@" + tempFile.getAbsolutePath();

    Processes.forkSync(null, System.out, System.err, args);
    tempFile.deleteOnExit();
  }
}