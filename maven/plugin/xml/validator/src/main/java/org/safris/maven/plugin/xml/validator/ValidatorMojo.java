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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.safris.commons.io.Files;
import org.safris.commons.util.DateUtil;
import org.safris.commons.xml.sax.SAXFeature;
import org.safris.commons.xml.sax.SAXParser;
import org.safris.commons.xml.sax.SAXParsers;
import org.safris.commons.xml.sax.SAXProperty;
import org.safris.commons.xml.validator.OfflineValidationException;
import org.safris.maven.common.AdvancedMojo;
import org.safris.maven.common.Log;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

  /* Alternate implementation, using modern interfaces... cant figure out how to
   * have it rely on xsi:schemaLocation attributes for schema paths.
  protected static void validate(final File dir, final File file, final Log log) throws IOException, SAXException {
    final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file, http, jar:file");
    factory.setFeature(SAXFeature.CONTINUE_AFTER_FATAL_ERROR.toString(), true);
    factory.setFeature(SAXFeature.SCHEMA_FULL_CHECKING.toString(), true);
    factory.setResourceResolver(new CachedResourceResolver());
    factory.setErrorHandler(ValidatorErrorHandler.getInstance(log));
    final Schema schema = factory.newSchema(new StreamSource(Resources.getResource("XMLSchema.xsd").openStream()));
    final Validator validator = schema.newValidator();
    validator.validate(new StreamSource(file));
  }*/

  protected static void validate(final File dir, final File file, final boolean offline) throws IOException, SAXException {
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
    saxParser.setProptery(SAXProperty.ENTITY_RESOLVER, new ValidatorEntityResolver(file.getAbsoluteFile().getParentFile(), offline));

    // Set the ErrorHandler.
    saxParser.setErrorHandler(ValidatorErrorHandler.instance());

    final String fileName = Files.relativePath(dir.getAbsoluteFile(), file.getAbsoluteFile());
    Log.info("   Validating: " + fileName);

    // Parse.
    saxParser.parse(new InputSource(new FileInputStream(file)));
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
        for (final File file : entry.getValue()) {
          final File recordFile = new File(recordDir, file.getName());
          if (recordFile.exists() && recordFile.lastModified() >= file.lastModified() && recordFile.lastModified() < file.lastModified() + DateUtil.MILLISECONDS_IN_DAY) {
            final String fileName = Files.relativePath(entry.getKey().getAbsoluteFile(), file.getAbsoluteFile());
            Log.info("Pre-validated: " + fileName);
          }
          else {
            try {
              validate(entry.getKey(), file, offline);
              if (!recordFile.createNewFile())
                recordFile.setLastModified(file.lastModified());
            }
            catch (final OfflineValidationException e) {
              if (!offline)
                throw e;
            }
            catch (final SAXException e) {
              throw new MojoFailureException("Failed to validate xml.", "", "\nFile: " + Files.relativePath(new File("").getAbsoluteFile(), file.getAbsoluteFile()) + "\nReason: " + e.getMessage() + "\n");
            }
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