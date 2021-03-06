/* Copyright (c) 2016 lib4j
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

package org.libx4j.maven.plugin.xml;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.lib4j.io.Files;
import org.libx4j.maven.common.MojoUtil;

@Mojo(name = "xml")
public abstract class XmlMojo extends AbstractMojo {
  protected static final File CWD = new File("").getAbsoluteFile();

  private static final String delimeter = "://";

  private static void convertToRegex(final List<String> list) {
    if (list != null)
      for (int i = 0; i < list.size(); i++)
        list.set(i, list.get(i).replace(".", "\\.").replace("**/", ".*").replace("/", "\\/").replace("*", ".*"));
  }

  protected static boolean filter(final File dir, final File pathname, final List<String> filters) {
    if (filters == null)
      return false;

    for (final String filter : filters)
      if (pathname.getAbsolutePath().substring(dir.getAbsolutePath().length() + 1).matches(filter))
        return true;

    return false;
  }

  protected static FileFilter filter(final File dir, final List<String> includes, final List<String> excludes) {
    return new FileFilter() {
      @Override
      public boolean accept(final File pathname) {
        if (!pathname.isFile())
          return false;

        if (includes == null && excludes == null)
          return pathname.getName().endsWith(".xml") || pathname.getName().endsWith(".xsd") || pathname.getName().endsWith(".xsl");

        return filter(dir, pathname, includes) && !filter(dir, pathname, excludes);
      }
    };
  }

  @Parameter(property = "includes")
  private List<String> includes;

  @Parameter(property = "excludes")
  private List<String> excludes;

  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  @Parameter(property = "skip", defaultValue = "false")
  private boolean skip;

  @Parameter(defaultValue = "${httpProxy}", required = false, readonly = true)
  private String httpProxy;

  @Parameter(defaultValue = "${project.resources}", required = true, readonly = true)
  private List<Resource> resources;

  @Parameter(defaultValue = "${project.testResources}", required = true, readonly = true)
  private List<Resource> testResources;

  @Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
  protected String directory = null;

  @Parameter(defaultValue = "${settings.offline}", required = true, readonly = true)
  protected boolean offline;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution mojoExecution;

  @Parameter(defaultValue = "${project}", readonly = true)
  protected MavenProject project;

  private File localDir;

  protected final File getLocalDir() {
    return localDir == null ? localDir = project.getBasedir() : localDir;
  }

  protected final void setHttpProxy() throws MojoFailureException {
    if (offline)
      return;

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
      getLog().info("Skipped.");
      return;
    }

    if (MojoUtil.shouldSkip(mojoExecution, mavenTestSkip)) {
      getLog().info("Tests are skipped.");
      return;
    }

    final Collection<Resource> resources = new ArrayList<Resource>();
    if (this.resources != null)
      resources.addAll(this.resources);

    if (testResources != null)
      resources.addAll(testResources);

    if (resources.size() == 0)
      return;

    convertToRegex(includes);
    convertToRegex(excludes);
    final LinkedHashSet<File> files = new LinkedHashSet<File>();
    for (final Resource resource : resources) {
      final File dir = new File(resource.getDirectory());
      final Collection<File> xmlFiles = Files.listAll(dir, filter(getLocalDir(), includes, excludes));
      if (xmlFiles != null)
        files.addAll(xmlFiles);
    }

    if (files.size() == 0)
      return;

    setHttpProxy();
    execute(files);
  }

  protected abstract void execute(final LinkedHashSet<File> files) throws MojoExecutionException, MojoFailureException;
}