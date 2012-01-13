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

package org.safris.commons.util.zip;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class Zips {
  private static final int WRITE_BUFFER_SIZE = 2048;
  private static final int READ_BUFFER_SIZE = 65536;

  public static void unzip(final File zipFile, final File targetDir) throws FileNotFoundException, IOException {
    Zips.unzip(zipFile, targetDir, null);
  }

  public static void unzip(final File zipFile, final File targetDir, final FileFilter filter) throws FileNotFoundException, IOException {
    if (zipFile == null)
      throw new NullPointerException("zipFile == null");

    if (targetDir == null)
      throw new NullPointerException("targetDir == null");

    targetDir.mkdirs();

    // read jar-file:
    final ZipFile zip = new ZipFile(zipFile);
    final String targetPath = targetDir.getAbsolutePath() + File.separator;
    final byte[] buffer = new byte[READ_BUFFER_SIZE];
    final Enumeration<? extends ZipEntry> enumeration = zip.entries();
    while (enumeration.hasMoreElements()) {
      final ZipEntry entry = enumeration.nextElement();
      if (entry.isDirectory())
        continue;

      // do not copy anything from the package cache:
      if (entry.getName().indexOf("package cache") != -1)
        continue;

      final File file = new File(targetPath + entry.getName());
      if (filter != null && !filter.accept(file))
        continue;

      if (!file.getParentFile().exists())
        file.getParentFile().mkdirs();

      final FileOutputStream out = new FileOutputStream(file);
      final InputStream in = zip.getInputStream(entry);
      int read;
      while ((read = in.read(buffer)) != -1)
        out.write(buffer, 0, read);

      in.close();
      out.close();
    }
  }

  public static String gunzip(final InputStream inputStream) throws IOException {
    if (inputStream == null)
      throw new NullPointerException("inputStream == null");

    final GZIPInputStream zipInputStream = new GZIPInputStream(new BufferedInputStream(inputStream));
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    int length;
    final byte[] buffer = new byte[READ_BUFFER_SIZE];
    while ((length = zipInputStream.read(buffer, 0, READ_BUFFER_SIZE)) != -1)
      outputStream.write(buffer, 0, length);

    zipInputStream.close();
    return new String(outputStream.toByteArray());
  }

  public static String gunzip(final byte[] bytes) throws IOException {
    if (bytes == null)
      throw new NullPointerException("bytes == null");

    return Zips.gunzip(new ByteArrayInputStream(bytes));
  }

  public static boolean add(final File zipFile, final Collection<CachedFile> files) throws IOException {
    if (zipFile == null)
      throw new NullPointerException("zipFile == null");

    if (files == null)
      throw new NullPointerException("files == null");

    if (files.size() == 0)
      return false;

    // get a temp file
    final File tempFile = File.createTempFile(zipFile.getName(), null);
    // delete it, otherwise you cannot rename your existing zip to it.
    tempFile.delete();

    final File file = new File(zipFile.getName());
    if (!file.renameTo(tempFile))
      throw new IOException("could not rename the file " + file.getAbsolutePath() + " to " + tempFile.getAbsolutePath());

    final byte[] bytes = new byte[WRITE_BUFFER_SIZE];

    final ZipInputStream in = new ZipInputStream(new FileInputStream(tempFile));
    final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));

    ZipEntry entry;
    WHILE:
    while ((entry = in.getNextEntry()) != null) {
      final String name = entry.getName();
      for (CachedFile fileWrapper : files)
        if (fileWrapper.getPath().equals(name))
          break WHILE;

      // Add ZIP entry to output stream.
      out.putNextEntry(new ZipEntry(name));
      // Transfer bytes from the ZIP file to the output file
      int length;
      while ((length = in.read(bytes)) > 0)
        out.write(bytes, 0, length);
    }

    // Close the streams
    in.close();
    // Compress the files
    for (CachedFile cachedFile : files) {
      // Add ZIP entry to output stream.
      out.putNextEntry(new ZipEntry(cachedFile.getPath()));
      // Transfer bytes from the file to the ZIP file
      out.write(cachedFile.getBytes());
      // Complete the entry
      out.closeEntry();
    }

    // Complete the ZIP file
    out.close();
    tempFile.delete();
    return true;
  }

  private Zips() {
  }
}
