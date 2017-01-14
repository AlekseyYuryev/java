package org.safris.maven.mojo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.maven.model.Build;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.safris.commons.net.URLs;

public class Manifest {
  private static PluginExecution getPluginExecution(final Plugin plugin, final MojoExecution mojoExecution) {
    for (final PluginExecution pluginExecution : plugin.getExecutions())
      if (pluginExecution.getId().equals(mojoExecution.getExecutionId()))
        return pluginExecution;

    return null;
  }

  public static Manifest parse(final MavenProject project, final MojoExecution mojoExecution, final boolean mavenTestSkip) throws MojoFailureException {
    if (mavenTestSkip && mojoExecution.getLifecyclePhase().contains("test"))
      return null;

    final Build build = project.getBuild();
    if (build == null || build.getPlugins() == null)
      throw new MojoFailureException("Configuration is required");

    final Plugin plugin = mojoExecution.getPlugin();
    plugin.flushExecutionMap();
    final PluginExecution pluginExecution = getPluginExecution(plugin, mojoExecution);
    if (pluginExecution.getPhase().contains("test") && mavenTestSkip)
      return null;

    final Xpp3Dom configuration = plugin.getConfiguration() == null ? (Xpp3Dom)pluginExecution.getConfiguration() : pluginExecution.getConfiguration() == null ? (Xpp3Dom)plugin.getConfiguration() : Xpp3Dom.mergeXpp3Dom((Xpp3Dom)plugin.getConfiguration(), (Xpp3Dom)pluginExecution.getConfiguration());
    if (configuration == null)
      throw new MojoFailureException("Configuration is required");

    return parse(configuration.getChild("manifest"), plugin, project);
  }

  private static URL buildURL(final File baseDir, final String path) throws MalformedURLException {
    if (URLs.isAbsolute(path))
      return URLs.makeUrlFromPath(path);

    if (baseDir != null)
      return new File(baseDir, path).toURI().toURL();

    return new File(path).toURI().toURL();
  }

  private static Manifest parse(final Xpp3Dom manifest, final Plugin plugin, final MavenProject project) throws MojoFailureException {
    if (manifest == null)
      throw new MojoFailureException("Manifest is required");

    File destdir = null;
    final LinkedHashSet<URL> schemas = new LinkedHashSet<URL>();
    final Set<String> excludes = new HashSet<String>();
    boolean overwrite = false;
    boolean compile = false;
    boolean pack = false;

    try {
      for (int j = 0; j < manifest.getChildCount(); j++) {
        final Xpp3Dom element = manifest.getChild(j);
        if ("destdir".equals(element.getName())) {
          destdir = new File(element.getValue());
          for (final String attribute : element.getAttributeNames()) {
            if (attribute.endsWith("overwrite"))
              overwrite = Boolean.parseBoolean(element.getAttribute(attribute));
            else if (attribute.endsWith("compile"))
              compile = Boolean.parseBoolean(element.getAttribute(attribute));
            else if (attribute.endsWith("package"))
              pack = Boolean.parseBoolean(element.getAttribute(attribute));
          }
        }
        else if ("schemas".equals(element.getName())) {
          for (int k = 0; k < element.getChildCount(); k++) {
            final Xpp3Dom schema = element.getChild(k);
            if ("schema".equals(schema.getName())) {
              schemas.add(buildURL(project.getFile().getParentFile().getAbsoluteFile(), schema.getValue()));
            }
          }
        }
        else if ("excludes".equals(element.getName())) {
          for (int k = 0; k < manifest.getChildCount(); k++) {
            final Xpp3Dom schema = manifest.getChild(k);
            if ("exclude".equals(schema.getName())) {
              excludes.add(schema.getValue());
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

    return new Manifest(overwrite, compile, pack, destdir, schemas, excludes);
  }

  private final boolean overwrite;
  private final boolean compile;
  private final boolean pack;
  private final File destdir;
  private final LinkedHashSet<URL> schemas;
  private final Set<String> excludes;

  public Manifest(final boolean overwrite, final boolean compile, final boolean pack, final File destdir, final LinkedHashSet<URL> schemas, final Set<String> excludes) {
    this.overwrite = overwrite;
    this.compile = compile;
    this.pack = pack;
    this.destdir = destdir;
    this.schemas = schemas;
    this.excludes = excludes;
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

  public Set<String> getExcludes() {
    return excludes;
  }
}