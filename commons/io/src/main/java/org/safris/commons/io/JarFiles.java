/* Copyright (c) 2017 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.commons.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.safris.commons.lang.Paths;

public class JarFiles {
  private static FileOutputStream getOutputStream(final File outDir, final String path) throws IOException {
    final String parent = Paths.getParent(path);
    final File destDir = parent == null || parent.length() == 0 ? outDir : new File(outDir, parent);
    if (!destDir.exists() && !destDir.mkdirs())
      throw new IOException("Unable to create destination directory: " + destDir.getAbsolutePath());

    return new FileOutputStream(new File(destDir, Paths.getName(path)));
  }

  public static void extract(final JarFile jarFile, final String path, final File outDir) throws IOException {
    final boolean isDirectory;
    final ZipEntry pathEntry;
    if (path.endsWith("/")) {
      isDirectory = true;
      pathEntry = null;
    }
    else {
      pathEntry = jarFile.getEntry(path + "/");
      isDirectory = pathEntry != null && pathEntry.isDirectory();
    }

    if (isDirectory) {
      try {
        jarFile.stream().forEach(new Consumer<JarEntry>() {
          @Override
          public void accept(final JarEntry t) {
            try {
              if (t.getName().startsWith(path)) {
                final FileOutputStream out = getOutputStream(outDir, t.getName());
                Streams.pipe(jarFile.getInputStream(t), out);
              }
            }
            catch (final IOException e) {
              throw new RuntimeException(e);
            }
          }
        });
      }
      catch (final RuntimeException e) {
        if (e.getCause() instanceof IOException)
          throw (IOException)e.getCause();
      }
    }
    else {
      final FileOutputStream out = getOutputStream(outDir, path);
      Streams.pipe(jarFile.getInputStream(pathEntry), out);
    }
  }
}