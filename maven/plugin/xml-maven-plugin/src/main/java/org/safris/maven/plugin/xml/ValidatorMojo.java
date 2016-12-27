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
import java.util.Iterator;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.safris.maven.common.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.COMPILE)
@Execute(goal = "validate")
public final class ValidatorMojo extends XmlMojo {
  private static SAXParser newParser() throws SAXException {
    final SAXParserFactory factory = SAXParserFactory.newInstance(SAXParserFactoryImpl.class.getName(), null);
    factory.setNamespaceAware(true);
    factory.setValidating(true);
    try {
      factory.setSchema(SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1").newSchema());
      factory.setFeature("http://xml.org/sax/features/validation", true);
      factory.setFeature("http://apache.org/xml/features/validation/schema", true);
      factory.setFeature("http://apache.org/xml/features/validation/dynamic", true);
      factory.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
      factory.setFeature("http://apache.org/xml/features/honour-all-schemaLocations", true);
      factory.setFeature("http://apache.org/xml/features/continue-after-fatal-error", true);

      return factory.newSAXParser();
    }
    catch (final ParserConfigurationException e) {
      throw new SAXException(e);
    }
  }

  protected static void validate(final File dir, final File file, final boolean offline) throws IOException, SAXException {
    validate(dir, file, offline, new LoggingErrorHandler());
  }

  protected static void validate(final File dir, final File file, final boolean offline, final ErrorHandler errorHandler) throws IOException, SAXException {
    final String fileName = Files.relativePath(dir.getAbsoluteFile(), file.getAbsoluteFile());
    Log.info("   Validating: " + fileName);

    final ParseHandler parseHandler = new ParseHandler(dir, offline, errorHandler);
    newParser().parse(file, parseHandler);
    if (parseHandler.getErrors() != null) {
      final Iterator<SAXParseException> iterator = parseHandler.getErrors().iterator();
      final SAXParseException firstException = iterator.next();
      final SAXException exception = new SAXException(firstException.getMessage(), firstException);
      while (iterator.hasNext())
        exception.addSuppressed(iterator.next());

      throw exception;
    }
  }

  @Override
  public void execute(final Set<File> files) throws MojoExecutionException, MojoFailureException {
    final File recordDir = new File(directory, "validator");
    StringBuilder errors = null;
    try {
      for (final File file : files) {
        final String relativePath = Files.relativePath(basedir.getAbsoluteFile(), file.getAbsoluteFile());
        final File recordFile = new File(recordDir, relativePath);
        recordFile.getParentFile().mkdirs();
        if (recordFile.exists() && recordFile.lastModified() >= file.lastModified() && recordFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
          final String fileName = Files.relativePath(basedir.getAbsoluteFile(), file.getAbsoluteFile());
          Log.info("Pre-validated: " + fileName);
        }
        else {
          try {
            validate(basedir, file, offline);
            recordFile.createNewFile();
            recordFile.setLastModified(file.lastModified());
          }
          catch (final SAXException e) {
            if (errors == null)
              errors = new StringBuilder();

            errors.append("\nFile: ").append(file.getAbsoluteFile());
          }
        }
      }
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
    catch (final OfflineValidationException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    if (errors != null)
      throw new MojoFailureException("Failed to validate xml.", "", errors.toString());
  }
}