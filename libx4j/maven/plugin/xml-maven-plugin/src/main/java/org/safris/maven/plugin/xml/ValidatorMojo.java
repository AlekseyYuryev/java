/* Copyright (c) 2008 lib4j
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

package org.safris.maven.plugin.xml;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.sax.Validator;
import org.safris.commons.xml.validate.OfflineValidationException;
import org.xml.sax.SAXException;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.COMPILE)
@Execute(goal = "validate")
public final class ValidatorMojo extends XmlMojo {
  @Override
  public void execute(final LinkedHashSet<File> files) throws MojoExecutionException, MojoFailureException {
    final File recordDir = new File(directory, "validator");
    recordDir.mkdirs();

    try {
      //log.info("Resource directory: " + entry.getKey().getAbsolutePath());
      for (final File file : files) {
        final File recordFile = new File(recordDir, file.getName());
        final String fileName = Files.relativePath(getLocalDir().getAbsoluteFile(), file.getAbsoluteFile());
        if (recordFile.exists() && recordFile.lastModified() >= file.lastModified() && recordFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
          getLog().info("Pre-validated: " + fileName);
        }
        else {
          try {
            getLog().info("   Validating: " + fileName);

            Validator.validate(file, offline);
            if (!recordFile.createNewFile())
              recordFile.setLastModified(file.lastModified());
          }
          catch (final OfflineValidationException e) {
            if (!offline)
              throw e;
          }
          catch (final SAXException e) {
            final StringBuilder builder = new StringBuilder("\nFile: " + file.getAbsoluteFile() + "\nReason: " + e.getMessage() + "\n");
            for (final Throwable t : e.getSuppressed())
              builder.append("       ").append(t.getMessage()).append("\n");

            throw new MojoFailureException("Failed to validate xml.", "", builder.toString());
          }
        }
      }
    }
    catch (final OfflineValidationException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}