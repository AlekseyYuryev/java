/* Copyright (c) 2008 Seva Safris
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.transform.Transformer;
import org.safris.commons.xml.validate.OfflineValidationException;
import org.safris.maven.common.Log;

@Mojo(name = "transform", defaultPhase = LifecyclePhase.COMPILE)
@Execute(goal = "transform")
public final class TransformMojo extends XmlMojo {
  @Parameter(property = "destdir", required = true)
  private String destdir;

  @Parameter(property = "rename")
  private String rename;

  @Parameter(property = "stylesheet")
  private File stylesheet;

  private static final Pattern replacePattern = Pattern.compile("^\\/((([^\\/])|(\\\\/))+)\\/((([^\\/])|(\\\\/))+)\\/$");

  @Override
  public void execute(final LinkedHashSet<File> files) throws MojoExecutionException, MojoFailureException {
    try {
      for (final File file : files) {
        final File destFile;
        if (rename != null) {
          final Matcher matcher = replacePattern.matcher(rename);
          if (!matcher.matches())
            throw new MojoExecutionException("<rename> tag must have a RegEx in the form: /<search>/<replace>/");

          final String search = matcher.group(1);
          final String replace = matcher.group(5);
          destFile = new File(getLocalDir(), destdir + File.separator + file.getName().replaceAll(search, replace));
        }
        else {
          destFile = file;
        }

        if (destFile.exists() && destFile.lastModified() >= file.lastModified() && destFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
          final String fileName = Files.relativePath(getLocalDir().getAbsoluteFile(), file.getAbsoluteFile());
          Log.info("Pre-transformed: " + fileName);
        }
        else {
          final String inFileName = Files.relativePath(getLocalDir().getAbsoluteFile(), file.getAbsoluteFile());
          final String outFileName = Files.relativePath(getLocalDir().getAbsoluteFile(), destFile.getAbsoluteFile());
          Log.info("   Transforming: " + inFileName + " -> " + outFileName);

          Transformer.transform(stylesheet.toURI().toURL(), file.toURI().toURL(), destFile);
        }
      }
    }
    catch (final OfflineValidationException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
    catch (final IOException | TransformerException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}