package org.safris.maven.plugin.version;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.safris.commons.io.Files;
import org.safris.commons.lang.Strings;
import org.safris.commons.maven.Log;

public class Transaction {
  private static final File tempDir = new File(new File(System.getProperty("java.io.tmpdir")), Transaction.class.getPackage().getName());

  static {
    tempDir.mkdir();
  }

  private final Map<File,File> realToTemp = new HashMap<File,File>();

  public void addFile(final File file, final byte[] contents) throws IOException {
    final File tempFile = new File(tempDir, file.getName() + "-" + Strings.getRandomAlphaNumericString(6));
    final RandomAccessFile raf = new RandomAccessFile(tempFile, "rw");
    raf.write(contents);
    raf.setLength(raf.getFilePointer());
    raf.close();
    realToTemp.put(file, tempFile);
  }

  public void commit() {
    try {
      for (final Map.Entry<File,File> entry : realToTemp.entrySet())
        java.nio.file.Files.move(entry.getValue().toPath(), entry.getKey().toPath(), StandardCopyOption.ATOMIC_MOVE);
    }
    catch (final IOException e) {
      throw new Error("Error encountered mid-commit", e);
    }

    try {
      Files.deleteAll(tempDir.toPath());
    }
    catch (final IOException e) {
      Log.warn("Failed to delete temp dir: " + tempDir.getAbsolutePath());
    }
  }
}