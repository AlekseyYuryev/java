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

package org.safris.commons.util.zip;

import java.io.BufferedInputStream;
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
import org.safris.commons.util.zip.CachedFile;

public final class Zips {
    private static final int WRITE_BUFFER_SIZE = 2048;
    private static final int READ_BUFFER_SIZE = 65536;

    public static void unzip(final File zipFile, final FileFilter filter, final File targetDir) throws FileNotFoundException, IOException {
        // clear target directory:
        if (targetDir.exists())
            targetDir.delete();

        // create new target directory:
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
            if (!filter.accept(file))
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

    public static String gunzip(InputStream inputStream) throws IOException {
        final GZIPInputStream zipInputStream = new GZIPInputStream(new BufferedInputStream(inputStream));
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int count;
        final byte[] buffer = new byte[READ_BUFFER_SIZE];
        while ((count = zipInputStream.read(buffer, 0, READ_BUFFER_SIZE)) != -1)
            outputStream.write(buffer, 0, count);

        zipInputStream.close();
        return new String(outputStream.toByteArray());
    }

    public static void add(File zipFile, Collection<CachedFile> files) throws IOException {
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
    }

    private Zips() {
    }
}
