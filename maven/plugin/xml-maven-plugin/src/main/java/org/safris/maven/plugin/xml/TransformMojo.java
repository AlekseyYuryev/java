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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.safris.maven.common.Log;

import net.sf.saxon.TransformerFactoryImpl;

@Mojo(name = "transform", defaultPhase = LifecyclePhase.COMPILE)
@Execute(goal = "transform")
public final class TransformMojo extends XmlMojo {
  @Parameter(property = "destdir", required = true)
  private String destdir;

  @Parameter(property = "rename")
  private String rename;

  @Parameter(property = "stylesheet")
  private File stylesheet;

  protected static void transform(final File stylesheet, final File dir, final File file, final File destFile) throws TransformerException {
    final String inFileName = Files.relativePath(dir.getAbsoluteFile(), file.getAbsoluteFile());
    final String outFileName = Files.relativePath(dir.getAbsoluteFile(), destFile.getAbsoluteFile());
    Log.info("   Transforming: " + inFileName + " -> " + outFileName);

    final TransformerFactory factory = TransformerFactory.newInstance(TransformerFactoryImpl.class.getName(), null);
    final Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));
    transformer.transform(new StreamSource(file), new StreamResult(destFile));
  }

  private static final Pattern replacePattern = Pattern.compile("^\\/((([^\\/])|(\\\\/))+)\\/((([^\\/])|(\\\\/))+)\\/$");

  @Override
  public void execute(final Set<File> files) throws MojoExecutionException, MojoFailureException {
    try {
      for (final File file : files) {
        final File destFile;
        if (rename != null) {
          final Matcher matcher = replacePattern.matcher(rename);
          if (!matcher.matches())
            throw new MojoExecutionException("<rename> tag must have a RegEx in the form: /<search>/<replace>/");

          final String search = matcher.group(1);
          final String replace = matcher.group(5);
          destFile = new File(destdir, file.getName().replaceAll(search, replace));
        }
        else {
          destFile = file;
        }

        if (destFile.exists() && destFile.lastModified() >= file.lastModified() && destFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
          final String fileName = Files.relativePath(basedir.getAbsoluteFile(), file.getAbsoluteFile());
          Log.info("Pre-transformed: " + fileName);
        }
        else {
          transform(stylesheet, basedir, file, destFile);
        }
      }
    }
    catch (final OfflineValidationException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
    catch (final TransformerException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}