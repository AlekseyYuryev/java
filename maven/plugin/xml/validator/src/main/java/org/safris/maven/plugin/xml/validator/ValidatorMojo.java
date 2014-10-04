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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.safris.commons.io.Files;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @goal validate
 * @requiresDependencyResolution test
 * @phase compile
 */
public final class ValidatorMojo extends AbstractMojo {
  private static final String delimeter = "://";

  private static final FileFilter xmlFileFilter = new FileFilter() {
    public boolean accept(final File pathname) {
      return pathname.isFile() && (pathname.getName().endsWith(".xml") || pathname.getName().endsWith(".xsd"));
    }
  };

  /**
   * @parameter default-value="" expression="${httpProxy}"
   */
  private String httpProxy;

  public String getHttpProxy() {
    return httpProxy;
  }

  /**
   * @parameter expression="${project.resources}"
   */
  private List<Resource> resources;

  public List<Resource> getResources() {
    return resources;
  }

  /**
   * @parameter expression="${project.testResources}"
   */
  private List<Resource> testResources;

  public List<Resource> getTestResources() {
    return testResources;
  }
  
  /**
   * @parameter
   */
  private List<String> includes;

  public List<String> getIncludes() {
    return includes;
  }

  /**
   * @parameter
   */
  private List<String> excludes;

  public List<String> getExcludes() {
    return excludes;
  }

  /**
   * @parameter default-value="${project.build.directory}"
   * @readonly
   * @required
   */
  private String directory = null;

  protected String getDirectory() {
    return directory;
  }

  protected static void validate(final File dir, final File file, final Log log) throws IOException, SAXException {
    final SAXParser saxParser = SAXParsers.createParser();
    // Set the features.
    saxParser.setFeature(SAXFeature.CONTINUE_AFTER_FATAL_ERROR, true);
    saxParser.setFeature(SAXFeature.DYNAMIC_VALIDATION, true);
    saxParser.setFeature(SAXFeature.NAMESPACE_PREFIXES, true);
    saxParser.setFeature(SAXFeature.NAMESPACES, true);
    saxParser.setFeature(SAXFeature.SCHEMA_FULL_CHECKING, true);
    saxParser.setFeature(SAXFeature.SCHEMA_VALIDATION, true);
    saxParser.setFeature(SAXFeature.WARN_ON_DUPLICATE_ATTDEF, true);
    saxParser.setFeature(SAXFeature.WARN_ON_DUPLICATE_ENTITYDEF, true);
    saxParser.setFeature(SAXFeature.VALIDATION, true);

    // Set the properties.
    saxParser.setProptery(SAXProperty.SCHEMA_LOCATION, "http://www.w3.org/2001/XMLSchema http://www.w3.org/2001/XMLSchema.xsd");
    saxParser.setProptery(SAXProperty.ENTITY_RESOLVER, new ValidatorEntityResolver(file.getAbsoluteFile().getParentFile()));

    // Set the ErrorHandler.
    saxParser.setErrorHandler(ValidatorErrorHandler.getInstance(log));

    final String fileName = Files.relativePath(dir.getAbsoluteFile(), file.getAbsoluteFile());
    if (log != null)
      log.info("Validating file: " + fileName);
    else
      System.out.println("Validating file: " + fileName);

    // Parse.
    saxParser.parse(new InputSource(new FileInputStream(file)));
  }

  protected void setHttpProxy() throws MojoExecutionException {
    final String httpProxy = getHttpProxy();
    if (httpProxy == null)
      return;

    final String scheme;
    if (httpProxy.startsWith("https" + delimeter))
      scheme = "https";
    else if (httpProxy.startsWith("http" + delimeter))
      scheme = "http";
    else
      throw new MojoExecutionException("Invalid proxy: " + httpProxy + " no http or http scheme.");

    final String port;
    final int portIndex = httpProxy.indexOf(":", scheme.length() + delimeter.length());
    if (portIndex != -1)
      port = httpProxy.substring(portIndex + 1);
    else
      port = "80";

    System.setProperty(scheme + ".proxyHost", httpProxy.substring(scheme.length() + delimeter.length(), portIndex));
    System.setProperty(scheme + ".proxyPort", port);
  }

  public void execute() throws MojoExecutionException, MojoFailureException {
    final Collection<Resource> resources = new ArrayList<Resource>();
    if (getResources() != null)
      resources.addAll(getResources());

    if (getTestResources() != null)
      resources.addAll(getTestResources());

    if (resources.size() == 0)
      return;

    final Map<File,Collection<File>> files = new HashMap<File,Collection<File>>();
    for (final Resource resource : resources) {
      final File dir = new File(resource.getDirectory());
      final Collection<File> xmlFiles = Files.listAll(dir, xmlFileFilter);
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
      final Log log = getLog();
      for (final Map.Entry<File,Collection<File>> entry : files.entrySet()) {
        log.info("Resource directory: " + entry.getKey().getAbsolutePath());
        for (final File file : entry.getValue()) {
          final File recordFile = new File(recordDir, file.getName());
          if (!recordFile.exists() || recordFile.lastModified() < file.lastModified()) {
            try {
              validate(entry.getKey(), file, log);
              if (!recordFile.createNewFile())
                recordFile.setLastModified(file.lastModified());
            }
            catch (final SAXException e) {
              throw new MojoFailureException("Failed to validate xml.", "\nFile: " + Files.relativePath(new File("").getAbsoluteFile(), file.getAbsoluteFile()), "Reason: " + e.getMessage() + "\n");
            }
          }
        }
      }
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }
  }
}