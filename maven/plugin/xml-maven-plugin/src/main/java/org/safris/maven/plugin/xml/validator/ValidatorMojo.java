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

package org.safris.maven.plugin.xml.validator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.common.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Mojo(name = "validate", defaultPhase = LifecyclePhase.COMPILE)
@Execute(goal = "validate")
public final class ValidatorMojo extends AdvancedMojo {
  private static final String delimeter = "://";

  private static void convertToRegex(final List<String> list) {
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        list.set(i, list.get(i).replace(".", "\\.").replace("**/", ".*").replace("/", "\\/").replace("*", ".*"));
  }

  public static boolean filter(final File dir, final File pathname, final List<String> filters) {
    if (filters == null)
      return false;

    for (final String filter : filters)
      if (pathname.getAbsolutePath().substring(dir.getAbsolutePath().length() + 1).matches(filter))
        return true;

    return false;
  }

  private static FileFilter filter(final File dir, final List<String> includes, final List<String> excludes) {
    return new FileFilter() {
      @Override
      public boolean accept(final File pathname) {
        if (!pathname.isFile())
          return false;

        if (includes == null && excludes == null)
          return pathname.getName().endsWith(".xml") || pathname.getName().endsWith(".xsd");

        return filter(dir, pathname, includes) && !filter(dir, pathname, excludes);
      }
    };
  }

  @Parameter(defaultValue = "${httpProxy}", required = false, readonly = true)
  private String httpProxy;

  public String getHttpProxy() {
    return httpProxy;
  }

  @Parameter(defaultValue = "${project.resources}", required = true, readonly = true)
  private List<Resource> resources;

  public List<Resource> getResources() {
    return resources;
  }

  @Parameter(defaultValue = "${project.testResources}", required = true, readonly = true)
  private List<Resource> testResources;

  public List<Resource> getTestResources() {
    return testResources;
  }

  @Parameter(property = "includes")
  private List<String> includes;

  public List<String> getIncludes() {
    return includes;
  }

  @Parameter(property = "excludes")
  private List<String> excludes;

  public List<String> getExcludes() {
    return excludes;
  }

  @Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
  private String directory = null;

  protected String getDirectory() {
    return directory;
  }

  @Parameter(defaultValue = "${settings.offline}", required = true, readonly = true)
  private boolean offline;

  @Parameter(property = "skip", defaultValue = "false")
  private boolean skip;

  private static Validator newValidator() throws SAXException {
    final SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
    return factory.newSchema().newValidator();
  }

  protected static void validate(final File dir, final File file, final boolean offline) throws IOException, SAXException {
    validate(dir, file, offline, newValidator());
  }

  protected static void validate(final File dir, final File file, final boolean offline, final Validator validator) throws IOException, SAXException {
    final String fileName = Files.relativePath(dir.getAbsoluteFile(), file.getAbsoluteFile());
    Log.info("   Validating: " + fileName);

    final BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file)) {
      @Override
      public void close() throws IOException {
        reset();
      }
    };
    inputStream.mark(Integer.MAX_VALUE);
    final StreamSource streamSource = new StreamSource(inputStream, file.toURI().toASCIIString());

    final CachedResourceResolver resolver = new CachedResourceResolver(dir, offline);
    validator.setResourceResolver(resolver);

    final ErrorHandler errorHandler = validator.getErrorHandler();

    final boolean[] hasError = new boolean[1];
    validator.setErrorHandler(new ErrorHandler() {
      @Override
      public void warning(final SAXParseException exception) throws SAXException {
      }

      @Override
      public void fatalError(final SAXParseException exception) throws SAXException {
      }

      @Override
      public void error(final SAXParseException exception) throws SAXException {
        hasError[0] = true;
      }
    });

    for (int i = 0; i < 2; i++) {
      validator.validate(streamSource);
      if (!hasError[0])
        return;

      if (i == 0)
        validator.setErrorHandler(errorHandler);
    }
  }

  protected void setHttpProxy() throws MojoFailureException {
    if (offline)
      return;

    final String httpProxy = getHttpProxy();
    if (httpProxy == null)
      return;

    final String scheme;
    if (httpProxy.startsWith("https" + delimeter))
      scheme = "https";
    else if (httpProxy.startsWith("http" + delimeter))
      scheme = "http";
    else
      throw new MojoFailureException("Invalid proxy: " + httpProxy + " no http or http scheme.");

    final int portIndex = httpProxy.indexOf(":", scheme.length() + delimeter.length());
    final String port = portIndex != -1 ? httpProxy.substring(portIndex + 1) : "80";

    System.setProperty(scheme + ".proxyHost", httpProxy.substring(scheme.length() + delimeter.length(), portIndex));
    System.setProperty(scheme + ".proxyPort", port);
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (skip) {
      Log.warn("Skipping.");
      return;
    }

    final Collection<Resource> resources = new ArrayList<Resource>();
    if (getResources() != null)
      resources.addAll(getResources());

    if (getTestResources() != null)
      resources.addAll(getTestResources());

    if (resources.size() == 0)
      return;

    convertToRegex(getIncludes());
    convertToRegex(getExcludes());
    final Map<File,Collection<File>> files = new HashMap<File,Collection<File>>();
    for (final Resource resource : resources) {
      final File dir = new File(resource.getDirectory());
      final Collection<File> xmlFiles = Files.listAll(dir, filter(dir, getIncludes(), getExcludes()));
      if (xmlFiles != null)
        files.put(dir, xmlFiles);
    }

    if (files.size() == 0)
      return;

    // Set the httpProxy if it was specified.
    setHttpProxy();

    final File recordDir = new File(getDirectory(), "validator");
    recordDir.mkdirs();

    try {
      for (final Map.Entry<File,Collection<File>> entry : files.entrySet()) {
        //log.info("Resource directory: " + entry.getKey().getAbsolutePath());
        final Validator validator = newValidator();
        for (final File file : entry.getValue()) {
          final File recordFile = new File(recordDir, file.getName());
          if (recordFile.exists() && recordFile.lastModified() >= file.lastModified() && recordFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
            final String fileName = Files.relativePath(entry.getKey().getAbsoluteFile(), file.getAbsoluteFile());
            Log.info("Pre-validated: " + fileName);
          }
          else {
            final boolean[] hasError = new boolean[1];
            try {
              validator.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(final SAXParseException exception) throws SAXException {
                  if (exception.getMessage() != null && exception.getMessage().startsWith("schema_reference.4"))
                    throw exception;

                  Log.warn(exception.getMessage() + " (" + exception.getLineNumber() + "," + exception.getColumnNumber() + ")");
                }

                @Override
                public void fatalError(final SAXParseException exception) throws SAXException {
                }

                @Override
                public void error(final SAXParseException exception) throws SAXException {
                  hasError[0] = true;
                  Log.error(exception.getMessage() + " (" + exception.getLineNumber() + "," + exception.getColumnNumber() + ")");
                }
              });
              validate(entry.getKey(), file, offline, validator);
              validator.reset();
              if (!recordFile.createNewFile())
                recordFile.setLastModified(file.lastModified());
            }
            catch (final OfflineValidationException e) {
              if (!offline)
                throw e;
            }

            if (hasError[0])
              throw new MojoFailureException("Failed to validate xml.", "", "\nFile: " + file.getAbsoluteFile());
          }
        }
      }
    }
    catch (final OfflineValidationException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }
    catch (final IOException | SAXException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}