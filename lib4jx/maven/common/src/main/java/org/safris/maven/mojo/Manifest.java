/* Copyright (c) 2017 lib4j
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

package org.safris.maven.mojo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;

import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.safris.commons.lang.Paths;
import org.safris.commons.net.URLs;

public class Manifest {
  public static Manifest parse(final MavenProject project, final MojoExecution mojoExecution) throws MojoFailureException {
    final Plugin plugin = mojoExecution.getPlugin();
    final PluginExecution pluginExecution = Mojos.getPluginExecution(mojoExecution);

    final Build build = project.getBuild();
    if (build == null || build.getPlugins() == null)
      throw new MojoFailureException("Configuration is required");

    final Xpp3Dom configuration = plugin.getConfiguration() == null ? (Xpp3Dom)pluginExecution.getConfiguration() : pluginExecution.getConfiguration() == null ? (Xpp3Dom)plugin.getConfiguration() : Xpp3Dom.mergeXpp3Dom((Xpp3Dom)plugin.getConfiguration(), (Xpp3Dom)pluginExecution.getConfiguration());
    return configuration == null ? null : parse(configuration.getChild("manifest"), plugin, project);
  }

  private static Manifest parse(final Xpp3Dom manifest, final Plugin plugin, final MavenProject project) throws MojoFailureException {
    if (manifest == null)
      throw new MojoFailureException("Manifest is required");

    File destdir = null;
    final LinkedHashSet<URL> resources = new LinkedHashSet<URL>();
    boolean overwrite = false;
    boolean compile = false;
    boolean pack = false;

    try {
      for (int j = 0; j < manifest.getChildCount(); j++) {
        final Xpp3Dom element = manifest.getChild(j);
        if ("destdir".equals(element.getName())) {
          destdir = Paths.isAbsolute(element.getValue()) ? new File(element.getValue()) : new File(project.getBuild().getDirectory(), element.getValue());
          for (final String attribute : element.getAttributeNames()) {
            if (attribute.endsWith("overwrite"))
              overwrite = Boolean.parseBoolean(element.getAttribute(attribute));
            else if (attribute.endsWith("compile"))
              compile = Boolean.parseBoolean(element.getAttribute(attribute));
            else if (attribute.endsWith("package"))
              pack = Boolean.parseBoolean(element.getAttribute(attribute));
          }
        }
        else if ("resources".equals(element.getName())) {
          for (int k = 0; k < element.getChildCount(); k++) {
            final Xpp3Dom schema = element.getChild(k);
            if ("resource".equals(schema.getName())) {
              resources.add(buildURL(project.getFile().getParentFile().getAbsoluteFile(), schema.getValue()));
            }
          }
        }
      }
    }
    catch (final IOException e) {
      throw new MojoFailureException(e.getMessage(), e);
    }

    if (destdir == null)
      throw new MojoFailureException("Manifest.destdir is required");

    return new Manifest(overwrite, compile, pack, destdir, resources);
  }

  private static URL buildURL(final File baseDir, final String path) throws MalformedURLException {
    if (URLs.isAbsolute(path))
      return URLs.makeUrlFromPath(path);

    if (baseDir != null)
      return new File(baseDir, path).toURI().toURL();

    return new File(path).toURI().toURL();
  }

  private final boolean overwrite;
  private final boolean compile;
  private final boolean pack;
  private final File destdir;
  private final LinkedHashSet<URL> schemas;

  public Manifest(final boolean overwrite, final boolean compile, final boolean pack, final File destdir, final LinkedHashSet<URL> schemas) {
    this.overwrite = overwrite;
    this.compile = compile;
    this.pack = pack;
    this.destdir = destdir;
    this.schemas = schemas;
  }

  public boolean getOverwrite() {
    return overwrite;
  }

  public boolean getCompile() {
    return compile;
  }

  public boolean getPackage() {
    return pack;
  }

  public File getDestdir() {
    return destdir;
  }

  public LinkedHashSet<URL> getSchemas() {
    return schemas;
  }
}