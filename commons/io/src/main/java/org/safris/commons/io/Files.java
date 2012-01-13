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

package org.safris.commons.io;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.safris.commons.lang.Paths;

public final class Files {
  private static final File CWD = new File("").getAbsoluteFile();

  private static final FileFilter anyFilter = new FileFilter() {
    public boolean accept(File pathname) {
      return true;
    }
  };

  private static class DirectoryFileFilter implements FileFilter {
    private final FileFilter original;

    public DirectoryFileFilter(FileFilter original) {
      this.original = original;
    }

    public boolean accept(File pathname) {
      return original.accept(pathname) || pathname.isDirectory();
    }
  }

  // FIXME: Implement this iteratively
  private static void deleteAll(File pathname, FileFilter filter, boolean onExit) throws IOException {
    if (pathname == null)
      return;

    if (onExit)
      pathname.deleteOnExit();
    else
      pathname.delete();

    if (!pathname.isDirectory())
      return;

    final File[] files = pathname.listFiles(filter);
    for (File file : files)
      deleteAll(file, filter, onExit);
  }

  public static void deleteAllOnExit(File pathname, FileFilter filter) throws IOException {
    deleteAll(pathname, filter, true);
  }

  public static void deleteAll(File pathname, FileFilter filter) throws IOException {
    deleteAll(pathname, filter, false);
  }

  public static void deleteAllOnExit(File pathname) throws IOException {
    deleteAll(pathname, anyFilter, true);
  }

  public static void deleteAll(File pathname) throws IOException {
    deleteAll(pathname, anyFilter, false);
  }

  public static File getCwd() {
    return CWD;
  }

  public static List<File> listAll(File directory) {
    if (!directory.isDirectory())
      return null;

    List<File> outer = new ArrayList<File>(Arrays.asList(directory.listFiles()));
    final List<File> files = new ArrayList<File>(outer);
    List<File> inner;
    while (outer.size() != 0) {
      inner = new ArrayList<File>();
      for (File file : outer)
        if (file.isDirectory())
          inner.addAll(Arrays.asList(file.listFiles()));

      files.addAll(inner);
      outer = inner;
    }

    return files;
  }

  public static List<File> listAll(File directory, FileFilter fileFilter) {
    if (!directory.isDirectory())
      return null;

    final FileFilter directoryFilter = new DirectoryFileFilter(fileFilter);
    List<File> outer = new ArrayList<File>(Arrays.asList(directory.listFiles(directoryFilter)));
    final List<File> files = new ArrayList<File>(outer);
    List<File> inner;
    while (outer.size() != 0) {
      inner = new ArrayList<File>();
      for (File file : outer)
        if (file.isDirectory())
          inner.addAll(Arrays.asList(file.listFiles(directoryFilter)));

      files.addAll(inner);
      outer = inner;
    }

    final List<File> result = new ArrayList<File>();
    for (File file : files)
      if (fileFilter.accept(file))
        result.add(file);

    return result;
  }

  /**
   * Copy a file or directory from <code>from</code> to <code>to</code>.
   *
   * @param from <code>File</code> to copy from.
   * @param to <code>File</code> to copy to.
   *
   * @exception IOException If there is an error handling either the from file, or the to file.
   */
  public static void copy(File from, File to) throws IOException {
    if (from == null)
      throw new NullPointerException("from == null");

    if (to == null)
      throw new NullPointerException("to == null");

    if (from.isFile())
      copyFile(from, to);
    else if (from.isDirectory()) {
      if (to.isFile())
        throw new IllegalArgumentException("trying to copy a directory to a file");

      if (!to.exists())
        to.mkdir();

      final List<File> files = Files.listAll(from.getAbsoluteFile());
      for (File file : files) {
        final String relativePath = Paths.relativePath(from.getAbsolutePath(), file.getAbsolutePath());
        final File toFile = new File(to, relativePath);
        if (file.isFile())
          copyFile(file, toFile);
        else if (file.isDirectory())
          toFile.mkdir();
        else
          throw new IllegalArgumentException(file.getAbsolutePath() + " does not exist");
      }
    }
    else
      throw new IllegalArgumentException("from does not exist");
  }

  private static void copyFile(File from, File to) throws IOException {
    final FileChannel sourceChannel = new FileInputStream(from).getChannel();
    final FileChannel destinationChannel = new FileOutputStream(to).getChannel();
    sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
    sourceChannel.close();
    destinationChannel.close();
  }

  public static String relativePath(File dir, File file) {
    if (dir == null || file == null)
      return null;

    // FIXME: Should this be getAbsolutePath() instead?
    return Paths.relativePath(dir.getPath(), file.getPath());
  }

  public static byte[] getBytes(File file) throws IOException {
    final InputStream in = new FileInputStream(file);

    // Get the size of the file
    final long length = file.length();

    // You cannot create an array using a long type.
    // It needs to be an int type.
    // Before converting to an int type, check
    // to ensure that file is not larger than Integer.MAX_VALUE.
    if (length > Integer.MAX_VALUE) {
      in.close();
      throw new IllegalArgumentException("File is too large to fit in a byte array");
    }

    // Create the byte array to hold the data
    final byte[] bytes = new byte[(int)length];

    // Read in the bytes
    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length    && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0)
      offset += numRead;

    // Close the input stream and return bytes
    in.close();

    // Ensure all the bytes have been read in
    if (offset < bytes.length)
      throw new IOException("Could not completely read file " + file.getName());

    return bytes;
  }

  public static void writeFile(File file, byte[] bytes) throws IOException {
    final FileOutputStream out = new FileOutputStream(file);
    out.write(bytes);
    out.close();
  }

  public static File commonality(File[] files) throws IOException {
    if (files == null || files.length == 0)
      return null;

    if (files.length > 1) {
      int length = Integer.MAX_VALUE;
      for (File file : files)
        if (file.getCanonicalPath().length() < length)
          length = file.getCanonicalPath().length();

      for (int i = 0; i < length; i++)
        for (int j = 1; j < files.length; j++)
          if (files[0].getCanonicalPath().charAt(i) != files[j].getCanonicalPath().charAt(i))
            return new File(files[0].getCanonicalPath().substring(0, i));
    }

    return files[0];
  }

  private Files() {
  }
}
